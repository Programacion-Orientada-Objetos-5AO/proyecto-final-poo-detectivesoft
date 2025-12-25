package ar.edu.huergo.gorodriguez.detectivesoft.service.turno;

import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;

public interface TurnoService {

    TurnoDto crearTurno(Long partidaId, Long jugadorId, int numeroTurno);

    TurnoDto obtenerTurnoPorId(Long id);

    List<TurnoDto> obtenerTodosLosTurnos();

    List<TurnoDto> obtenerTurnosPorPartida(Long partidaId);

    List<TurnoDto> obtenerTurnosPorJugador(Long jugadorId);

    TurnoDto finalizarTurno(Long turnoId);

    void eliminarTurno(Long id);

    TurnoDto actualizarTurno(Long id, TurnoDto turnoDto);
}
