package ar.edu.huergo.gorodriguez.detectivesoft.service.turno;

import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;

public interface TurnoService {

    // Crear nuevo turno
    TurnoDto crearTurno(Long partidaId, Long jugadorId, int numeroTurno);

    // Obtener turno por ID
    TurnoDto obtenerTurnoPorId(Long id);

    
    // Listar todos los turnos
    List<TurnoDto> obtenerTodosLosTurnos();

    // Listar todos los turnos de una partida
    List<TurnoDto> obtenerTurnosPorPartida(Long partidaId);

    // Listar todos los turnos de un jugador
    List<TurnoDto> obtenerTurnosPorJugador(Long jugadorId);

    // Finalizar un turno
    TurnoDto finalizarTurno(Long turnoId);

    // Eliminar un turno
    void eliminarTurno(Long id);

    // Actualizar un turno
    TurnoDto actualizarTurno(Long id, TurnoDto turnoDto);
}
