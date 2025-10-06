package ar.edu.huergo.gorodriguez.detectivesoft.controller.anotador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.security.MensajeDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.anotador.AnotadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anotadores")
public class AnotadorController {

    private final AnotadorService anotadorService;

    // Crear un nuevo anotador
    @PostMapping("/crear/{jugadorId}/{partidaId}")
    public ResponseEntity<AnotadorDto> crearAnotador(
            @PathVariable Long jugadorId,
            @PathVariable Long partidaId) {
        AnotadorDto nuevoAnotador = anotadorService.crearAnotador(jugadorId, partidaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAnotador);
    }

    // Obtener un anotador por ID
    @GetMapping("/{id}")
    public ResponseEntity<AnotadorDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(anotadorService.obtenerPorId(id));
    }

    // Listar anotadores por partida
    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<AnotadorDto>> listarPorPartida(@PathVariable Long partidaId) {
        List<AnotadorDto> anotadores = anotadorService.listarPorPartida(partidaId);
        return ResponseEntity.ok(anotadores);
    }

    // Actualizar cartas descartadas de un anotador
    @PutMapping("/{anotadorId}/descartadas")
    public ResponseEntity<AnotadorDto> actualizarCartasDescartadas(
            @PathVariable Long anotadorId,
            @Valid @RequestBody List<Long> nuevasCartasDescartadas) {
        return ResponseEntity.ok(anotadorService.actualizarCartasDescartadas(anotadorId, nuevasCartasDescartadas));
    }

    // Eliminar un anotador
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDto> eliminarAnotador(@PathVariable Long id) {
        anotadorService.eliminarAnotador(id);
        return ResponseEntity.ok(new MensajeDto("Anotador eliminado correctamente."));
    }
}
