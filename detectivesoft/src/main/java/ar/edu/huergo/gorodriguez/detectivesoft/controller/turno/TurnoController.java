package ar.edu.huergo.gorodriguez.detectivesoft.controller.turno;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.turno.TurnoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    // Obtener todos los turnos
    @GetMapping
    public ResponseEntity<List<TurnoDto>> obtenerTodos() {
        return ResponseEntity.ok(turnoService.obtenerTodosLosTurnos());
    }

    // Obtener turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerTurnoPorId(id));
    }

    // Crear un turno
    @PostMapping
    public ResponseEntity<TurnoDto> crearTurno(@RequestBody TurnoDto turnoDto) {
        TurnoDto nuevo = turnoService.crearTurno(turnoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Actualizar turno
    @PutMapping("/{id}")
    public ResponseEntity<TurnoDto> actualizarTurno(
            @PathVariable Long id,
            @RequestBody TurnoDto turnoDto) {
        return ResponseEntity.ok(turnoService.actualizarTurno(id, turnoDto));
    }

    // Eliminar turno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }
}
