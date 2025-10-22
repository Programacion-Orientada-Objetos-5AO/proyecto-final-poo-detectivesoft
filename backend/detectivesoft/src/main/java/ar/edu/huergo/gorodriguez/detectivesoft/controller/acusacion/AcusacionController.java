package ar.edu.huergo.gorodriguez.detectivesoft.controller.acusacion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.security.MensajeDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.acusacion.AcusacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/acusaciones")

public class AcusacionController {
    
    private final AcusacionService acusacionService;

    @PostMapping
    public ResponseEntity<?> crearAcusacion(@Valid @RequestBody AcusacionDto dto) {
        try {
            AcusacionDto creada = acusacionService.crearAcusacion(dto);

            if (Boolean.TRUE.equals(creada.getCorrecta())) {
                return ResponseEntity.ok(new MensajeDto("¡Acusación correcta! Ganaste la partida."));
            } else {
                return ResponseEntity.ok(new MensajeDto("Acusación incorrecta. Quedás fuera de la partida."));
            }

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeDto(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<AcusacionDto>> obtenerTodas() {
        return ResponseEntity.ok(acusacionService.obtenerTodas());
    }

    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<AcusacionDto>> obtenerPorPartida(@PathVariable("partidaId") Long partidaId) {
        return ResponseEntity.ok(acusacionService.obtenerPorPartida(partidaId));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<AcusacionDto>> obtenerPorJugador(@PathVariable("jugadorId") Long jugadorId) {
        return ResponseEntity.ok(acusacionService.obtenerPorJugador(jugadorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDto> eliminarAcusacion(@PathVariable("id") Long id){
        acusacionService.eliminarAcusacion(id);
        return ResponseEntity.ok(new MensajeDto("Acusacion eliminada correctamente."));
    }
}
