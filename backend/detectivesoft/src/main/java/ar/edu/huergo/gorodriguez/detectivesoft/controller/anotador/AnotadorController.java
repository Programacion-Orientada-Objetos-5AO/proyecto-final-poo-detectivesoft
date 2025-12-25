package ar.edu.huergo.gorodriguez.detectivesoft.controller.anotador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.anotador.AnotadorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/anotadores")
@RequiredArgsConstructor
public class AnotadorController {

    private final AnotadorService anotadorService;

    @PostMapping("/crear/{jugadorId}/{partidaId}")
    public ResponseEntity<AnotadorDto> crearAnotador(
            @PathVariable("jugadorId") Long jugadorId,
            @PathVariable("partidaId") Long partidaId) {
        return ResponseEntity.ok(anotadorService.crearAnotador(jugadorId, partidaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnotadorDto> obtenerPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(anotadorService.obtenerPorId(id));
    }

    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<AnotadorDto>> listarPorPartida(@PathVariable("partidaId") Long partidaId) {
        return ResponseEntity.ok(anotadorService.listarPorPartida(partidaId));
    }

    @PutMapping("/{anotadorId}/cartas")
    public ResponseEntity<AnotadorDto> actualizarCartasDescartadas(
            @PathVariable("anotadorId") Long anotadorId,
            @RequestBody List<Long> nuevasCartasDescartadas) {
        return ResponseEntity.ok(anotadorService.actualizarCartasDescartadas(anotadorId, nuevasCartasDescartadas));
    }

    @PostMapping("/{jugadorId}/descartar/{cartaId}")
    public ResponseEntity<Void> marcarCartaComoDescartada(
            @PathVariable Long jugadorId,
            @PathVariable Long cartaId) {
        anotadorService.marcarCartaComoDescartada(jugadorId, cartaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnotador(@PathVariable("id") Long id) {
        anotadorService.eliminarAnotador(id);
        return ResponseEntity.noContent().build();
    }
}
