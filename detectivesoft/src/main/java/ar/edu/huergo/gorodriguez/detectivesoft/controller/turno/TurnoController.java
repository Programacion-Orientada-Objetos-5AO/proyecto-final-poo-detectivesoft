package ar.edu.huergo.gorodriguez.detectivesoft.controller.turno;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.security.MensajeDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.turno.TurnoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    // Crear turno
    @PostMapping("/crear/{partidaId}/{jugadorId}/{numeroTurno}")
    public ResponseEntity<TurnoDto> crearTurno(
            @PathVariable("partidaId") Long partidaId,
            @PathVariable("jugadorId") Long jugadorId,
            @PathVariable("numeroTurno") int numeroTurno) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crearTurno(partidaId, jugadorId, numeroTurno));
    }

    // Obtener turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> obtenerTurnoPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(turnoService.obtenerTurnoPorId(id));
    }

    // Listar todos los turnos
    @GetMapping
    public ResponseEntity<List<TurnoDto>> obtenerTodosLosTurnos() {
        return ResponseEntity.ok(turnoService.obtenerTodosLosTurnos());
    }

    // Listar turnos de una partida
    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<TurnoDto>> obtenerTurnosPorPartida(@PathVariable("partidaId") Long partidaId) {
        return ResponseEntity.ok(turnoService.obtenerTurnosPorPartida(partidaId));
    }

    // Listar turnos de un jugador
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<TurnoDto>> obtenerTurnosPorJugador(@PathVariable("jugadorId") Long jugadorId) {
        return ResponseEntity.ok(turnoService.obtenerTurnosPorJugador(jugadorId));
    }

    // Finalizar turno
    @PostMapping("/finalizar/{turnoId}")
    public ResponseEntity<TurnoDto> finalizarTurno(@PathVariable("turnoId") Long turnoId) {
        return ResponseEntity.ok(turnoService.finalizarTurno(turnoId));
    }

    // Actualizar turno
    @PutMapping("/{id}")
    public ResponseEntity<TurnoDto> actualizarTurno(
            @PathVariable("id") Long id,
            @RequestBody TurnoDto turnoDto) {
        return ResponseEntity.ok(turnoService.actualizarTurno(id, turnoDto));
    }

    // Eliminar turno
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDto> eliminarTurno(@PathVariable("id") Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok(new MensajeDto("Turno eliminado correctamente."));
    }
}