package ar.edu.huergo.gorodriguez.detectivesoft.service.turno;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.turno.TurnoMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.turno.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TurnoMapper turnoMapper;

    @Override
    public TurnoDto crearTurno(Long partidaId, Long jugadorId, int numeroTurno) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        Turno turno = new Turno();
        turno.setPartida(partida);
        turno.setJugador(jugador);
        turno.setNumeroTurno(numeroTurno);
        turno.setActivo(true);
        turno.setFechaInicio(LocalDateTime.now());

        return turnoMapper.toDto(turnoRepository.save(turno));
    }

    @Override
    public TurnoDto obtenerTurnoPorId(Long id) {
        return turnoRepository.findById(id)
                .map(turnoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));
    }

    @Override
    public List<TurnoDto> obtenerTodosLosTurnos() {
        return turnoMapper.toDtoList(turnoRepository.findAll());
    }

    @Override
    public List<TurnoDto> obtenerTurnosPorPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return turnoMapper.toDtoList(turnoRepository.findByPartida(partida));
    }

    @Override
    public List<TurnoDto> obtenerTurnosPorJugador(Long jugadorId) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
        return turnoMapper.toDtoList(turnoRepository.findByJugador(jugador));
    }

    @Override
    public TurnoDto finalizarTurno(Long turnoId) {
        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));

        turno.setActivo(false);
        turno.setFechaFin(LocalDateTime.now());

        return turnoMapper.toDto(turnoRepository.save(turno));
    }

    @Override
    public void eliminarTurno(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));
        turnoRepository.delete(turno);
    }

    @Override
    public TurnoDto actualizarTurno(Long id, TurnoDto turnoDto) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));

        turno.setNumeroTurno(turnoDto.getNumeroTurno());
        turno.setActivo(turnoDto.isActivo());

        return turnoMapper.toDto(turnoRepository.save(turno));
    }
}
