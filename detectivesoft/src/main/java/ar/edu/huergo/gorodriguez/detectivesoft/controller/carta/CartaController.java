package ar.edu.huergo.gorodriguez.detectivesoft.controller.carta;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import jakarta.validation.Valid;


import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.carta.CartaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cartas")
public class CartaController {

    private final CartaService cartaService;

    @PostMapping
    public ResponseEntity<CartaDto> crearCarta(@Valid @RequestBody CartaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaService.crearCarta(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> obtenerCartaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cartaService.obtenerCartaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CartaDto>> obtenerTodasLasCartas() {
        return ResponseEntity.ok(cartaService.obtenerTodasLasCartas());
    }

    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<CartaDto>> obtenerCartasPorPartida(@PathVariable Long partidaId) {
        return ResponseEntity.ok(cartaService.obtenerCartasPorPartida(partidaId));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<CartaDto>> obtenerCartasPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(cartaService.obtenerCartasPorJugador(jugadorId));
    }

    @GetMapping("/sin-repartir/{partidaId}")
    public ResponseEntity<List<CartaDto>> obtenerCartasSinRepartir(@PathVariable Long partidaId) {
        return ResponseEntity.ok(cartaService.obtenerCartasSinRepartir(partidaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaDto> actualizarCarta(
            @PathVariable Long id,
            @Valid @RequestBody CartaDto dto) {
        return ResponseEntity.ok(cartaService.actualizarCarta(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarta(@PathVariable Long id) {
        cartaService.eliminarCarta(id);
        return ResponseEntity.noContent().build();
    }
}