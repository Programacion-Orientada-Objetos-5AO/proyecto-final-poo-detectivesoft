package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta.TipoCarta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida.PartidaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.turno.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final CartaRepository cartaRepository;
    private final TurnoRepository turnoRepository;
    private final PartidaMapper partidaMapper;

    @Override
    public PartidaDto crearPartida(Long creadorId) {
        Jugador creador = jugadorRepository.findById(creadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        Partida partida = new Partida();
        partida.setCodigo(generarCodigoUnico());
        partida.setEstado(EstadoPartida.PENDIENTE);
        partida.setFechaCreacion(LocalDateTime.now());
        partida.setMaxJugadores(6);
        partida.agregarJugador(creador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    @Override
    public PartidaDto unirseAPartida(String codigo) {
        Partida partida = partidaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("No hay un jugador autenticado.");
        }
        
        String username = auth.getName();

        Jugador jugador = jugadorRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Jugador autenticado no encontrado"));

        if (partida.getJugadores().contains(jugador)) {
            throw new IllegalStateException("El jugador ya está en la partida");
        }
        if (partida.getEstado() != EstadoPartida.PENDIENTE) {
            throw new IllegalStateException("No puedes unirte a una partida ya iniciada o finalizada.");
        }
        if (partida.getJugadores().size() >= partida.getMaxJugadores()) {
            throw new IllegalStateException("La partida ya está llena");
        }

        partida.agregarJugador(jugador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    @Override
    public List<PartidaDto> listarPartidas() {
        return partidaRepository.findAll()
                .stream()
                .map(partidaMapper::toDto)
                .toList();
    }

    @Override
    public PartidaDto obtenerPartidaPorId(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return partidaMapper.toDto(partida);
    }

    @Override
    public void eliminarPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        partida.getJugadores().clear();
        partidaRepository.delete(partida);
    }

    @Override
    public PartidaDto eliminarJugadorDePartida(Long partidaId, Long jugadorId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        partida.removerJugador(jugador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    public List<PartidaDto> listarPartidasPorEstado(EstadoPartida estado) {
        return partidaRepository.findByEstado(estado.name())
                .stream()
                .map(partidaMapper::toDto)
                .toList();
    }

    public PartidaDto actualizarEstadoPartida(Long partidaId, EstadoPartida nuevoEstado) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        partida.setEstado(nuevoEstado);
        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    private String generarCodigoUnico() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    @Override
    public PartidaDto iniciarPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        if (partida.getJugadores().size() < 2) {
            throw new IllegalStateException("Debe haber al menos 2 jugadores para iniciar la partida");
        }

        if (partida.getEstado() != EstadoPartida.PENDIENTE) {
            throw new IllegalStateException("La partida ya ha sido iniciada o finalizada");
        }

        // Cambiar estado
        partida.setEstado(EstadoPartida.EN_CURSO);

        // Obtener y separar cartas
        List<Carta> cartas = cartaRepository.findByPartidaIsNull();
        if (cartas.isEmpty()) {
            throw new IllegalStateException("No hay cartas disponibles para repartir");
        }

        List<Carta> personajes = cartas.stream()
                .filter(c -> c.getTipo() == TipoCarta.PERSONAJE)
                .toList();
        List<Carta> armas = cartas.stream()
                .filter(c -> c.getTipo() == TipoCarta.ARMA)
                .toList();
        List<Carta> habitaciones = cartas.stream()
                .filter(c -> c.getTipo() == TipoCarta.HABITACION)
                .toList();

        // Elegir cartas culpables
        Random random = new Random();
        Carta personajeCulpable = personajes.get(random.nextInt(personajes.size()));
        Carta armaCulpable = armas.get(random.nextInt(armas.size()));
        Carta habitacionCulpable = habitaciones.get(random.nextInt(habitaciones.size()));

        // Guardar cartas culpables en la partida
        partida.setCartaCulpablePersonaje(personajeCulpable);
        partida.setCartaCulpableArma(armaCulpable);
        partida.setCartaCulpableHabitacion(habitacionCulpable);

        // Cartas a repartir
        partida.getJugadores().forEach(j -> j.getCartas().clear());
        List<Carta> cartasRepartibles = cartas.stream()
                .filter(c -> !List.of(personajeCulpable, armaCulpable, habitacionCulpable).contains(c))
                .collect(Collectors.toList());

        Collections.shuffle(cartasRepartibles);

        // Repartir entre jugadores
        int numJugadores = partida.getJugadores().size();
        int index = 0;
        for (Carta carta : cartasRepartibles) {
            Jugador jugador = partida.getJugadores().get(index % numJugadores);
            carta.setJugador(jugador);
            carta.setPartida(partida);
            jugador.getCartas().add(carta);
            index++;
        }

        Jugador jugadorInicial = partida.getJugadores().get(random.nextInt(numJugadores));

        // Crear primer turno
        Turno primerTurno = new Turno();
        primerTurno.setPartida(partida);
        primerTurno.setJugador(jugadorInicial);
        primerTurno.setNumeroTurno(1);
        primerTurno.setActivo(true);
        primerTurno.setFechaInicio(LocalDateTime.now());

        partidaRepository.save(partida);
        turnoRepository.save(primerTurno);
        partida.setTurnoActual(primerTurno);
        partida.getTurnos().add(primerTurno);
        partidaRepository.save(partida);

        cartaRepository.saveAll(cartasRepartibles);
        partidaRepository.save(partida);

        log.info("Partida {} iniciada con {} jugadores", partida.getCodigo(), partida.getJugadores().size());
        log.info("Culpables: {} - {} - {}", personajeCulpable.getNombre(), armaCulpable.getNombre(), habitacionCulpable.getNombre());


        return partidaMapper.toDto(partida);
    }
}