package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida.PartidaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final CartaRepository cartaRepository;
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
    public PartidaDto unirseAPartida(String codigo, Long jugadorId) {
        Partida partida = partidaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        if (partida.getJugadores().contains(jugador)) {
            throw new IllegalStateException("El jugador ya está en la partida");
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

        // Cambiar estado de la partida a EN_CURSO
        partida.setEstado(EstadoPartida.EN_CURSO);

        // Obtener todas las cartas disponibles
        List<Carta> cartas = cartaRepository.findAll();

        if (cartas.isEmpty()) {
            throw new IllegalStateException("No hay cartas disponibles para repartir");
        }

        // Mezclar las cartas
        Collections.shuffle(cartas);

        // Repartir cartas entre los jugadores
        int numJugadores = partida.getJugadores().size();
        int index = 0;
        for (Carta carta : cartas) {
            Jugador jugador = partida.getJugadores().get(index % numJugadores);
            carta.setJugador(jugador);
            jugador.getCartas().add(carta);
            index++;
        }

        // Seleccionar el jugador inicial (por ejemplo, el primero en la lista)
        Jugador jugadorInicial = partida.getJugadores().get(0);

        // Crear el primer turno
        Turno primerTurno = new Turno();
        primerTurno.setPartida(partida);
        primerTurno.setJugador(jugadorInicial);
        primerTurno.setNumeroTurno(1);
        primerTurno.setActivo(true);
        primerTurno.setFechaInicio(LocalDateTime.now());

        partida.getTurnos().add(primerTurno);
        partida.setTurnoActual(primerTurno);

        cartaRepository.saveAll(cartas);
        partidaRepository.save(partida);


        return partidaMapper.toDto(partida);
    }


    
}