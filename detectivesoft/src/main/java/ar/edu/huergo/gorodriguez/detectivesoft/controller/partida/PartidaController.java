package ar.edu.huergo.gorodriguez.detectivesoft.controller.partida;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.partida.PartidaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    // Crear partida
    @PostMapping("/crear/{creadorId}")
    public ResponseEntity<PartidaDto> crearPartida(@PathVariable Long creadorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partidaService.crearPartida(creadorId));
    }

    // Unirse a partida
    @PostMapping("/unirse/{codigo}/{jugadorId}")
    public ResponseEntity<PartidaDto> unirseAPartida(
            @PathVariable String codigo,
            @PathVariable Long jugadorId) {
        return ResponseEntity.ok(partidaService.unirseAPartida(codigo, jugadorId));
    }

    // Listar partidas
    @GetMapping
    public ResponseEntity<List<PartidaDto>> listarPartidas() {
        return ResponseEntity.ok(partidaService.listarPartidas());
    }

    // Obtener partida por ID
    @GetMapping("/{id}")
    public ResponseEntity<PartidaDto> obtenerPartidaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.obtenerPartidaPorId(id));
    }

    // Eliminar partida por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPartida(@PathVariable Long id) {
        partidaService.eliminarPartida(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar jugador de una partida
    @DeleteMapping("/{partidaId}/jugadores/{jugadorId}")
    public ResponseEntity<PartidaDto> eliminarJugadorDePartida(
            @PathVariable Long partidaId,
            @PathVariable Long jugadorId) {
        return ResponseEntity.ok(partidaService.eliminarJugadorDePartida(partidaId, jugadorId));
    }
}
