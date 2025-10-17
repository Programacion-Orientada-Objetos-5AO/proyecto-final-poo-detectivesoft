package ar.edu.huergo.gorodriguez.detectivesoft.service.acusacion;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.acusacion.AcusacionMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion.AcusacionRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcusacionServiceImpl implements AcusacionService {

    private final AcusacionRepository acusacionRepository;
    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final CartaRepository cartaRepository;
    private final AcusacionMapper acusacionMapper;

    @Override
    public AcusacionDto crearAcusacion(AcusacionDto dto) {

        // Obtener jugador autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("No hay un jugador autenticado.");
        }
        String username = auth.getName();

        Jugador jugador = jugadorRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Jugador autenticado no encontrado"));

        // Buscar la partida
        Partida partida = partidaRepository.findById(dto.getPartidaId())
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        
        // Verificar estado de la partida
        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("La partida ya ha finalizado.");
        }

        // Verificar turno
        Long idTurno = partida.getTurnoActual().getJugador().getId();
        if (!idTurno.equals(jugador.getId())) {
            throw new IllegalStateException("No es tu turno para hacer una acusación.");
        }

        // Buscar cartas
        var arma = cartaRepository.findById(dto.getArmaId())
                .orElseThrow(() -> new EntityNotFoundException("Carta de arma no encontrada"));
        var habitacion = cartaRepository.findById(dto.getHabitacionId())
                .orElseThrow(() -> new EntityNotFoundException("Carta de habitación no encontrada"));
        var personaje = cartaRepository.findById(dto.getPersonajeId())
                .orElseThrow(() -> new EntityNotFoundException("Carta de personaje no encontrada"));

        // Crear acusación
        Acusacion acusacion = new Acusacion();
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);
        acusacion.setArma(arma);
        acusacion.setHabitacion(habitacion);
        acusacion.setPersonaje(personaje);
        acusacion.setFecha(LocalDateTime.now());

        // Validar si es correcta
        boolean esCorrecta =
                partida.getCartaCulpableArma().getId().equals(arma.getId()) &&
                partida.getCartaCulpableHabitacion().getId().equals(habitacion.getId()) &&
                partida.getCartaCulpablePersonaje().getId().equals(personaje.getId());

        acusacion.setCorrecta(esCorrecta);

        // Guardar acusación
        Acusacion guardada = acusacionRepository.save(acusacion);

        if (esCorrecta) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
        } else {
            avanzarTurno(partida);
            partidaRepository.save(partida);
        }

        return acusacionMapper.toDto(guardada);
    }

    private void avanzarTurno(Partida partida) {
        List<Jugador> jugadores = partida.getJugadores();
        if (jugadores == null || jugadores.isEmpty()) return;

        Turno turnoActual = partida.getTurnoActual();
        if (turnoActual != null) {
            turnoActual.setActivo(false);
            turnoActual.setFechaFin(LocalDateTime.now());
        }

        // Determinar el siguiente jugador
        Jugador jugadorActual = turnoActual.getJugador();
        int indiceActual = jugadores.indexOf(jugadorActual);
        int siguienteIndice = (indiceActual + 1) % jugadores.size();

        Jugador siguienteJugador = jugadores.get(siguienteIndice);

        // Crear y asignar el nuevo turno
        Turno nuevoTurno = new Turno();
        nuevoTurno.setPartida(partida);
        nuevoTurno.setJugador(siguienteJugador);
        nuevoTurno.setNumeroTurno(turnoActual.getNumeroTurno() + 1);
        nuevoTurno.setActivo(true);
        nuevoTurno.setFechaInicio(LocalDateTime.now());

        partida.getTurnos().add(nuevoTurno);
        partida.setTurnoActual(nuevoTurno);
        partidaRepository.save(partida);
    }

    @Override
    public List<AcusacionDto> obtenerTodas() {
        return acusacionMapper.toDtoList(acusacionRepository.findAll());
    }

    @Override
    public List<AcusacionDto> obtenerPorPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return acusacionMapper.toDtoList(acusacionRepository.findByPartida(partida));
    }

    @Override
    public List<AcusacionDto> obtenerPorJugador(Long jugadorId) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
        return acusacionMapper.toDtoList(acusacionRepository.findByJugador(jugador));
    }

    @Override
    public boolean existeAcusacionCorrecta(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return !acusacionRepository.findByPartidaAndCorrectaTrue(partida).isEmpty();
    }

    @Override
    public void eliminarAcusacion(Long id) {
        if (!acusacionRepository.existsById(id)) {
            throw new EntityNotFoundException("Acusación no encontrada");
        }
        acusacionRepository.deleteById(id);
    }
}