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

    // Crear una nueva carta
    @PostMapping
    public ResponseEntity<CartaDto> crearCarta(@Valid @RequestBody CartaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaService.crearCarta(dto));
    }

    // Obtener una carta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> obtenerCartaPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cartaService.obtenerCartaPorId(id));
    }

    // Obtener todas las cartas
    @GetMapping
    public ResponseEntity<List<CartaDto>> obtenerTodasLasCartas() {
        return ResponseEntity.ok(cartaService.obtenerTodasLasCartas());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CartaDto>> obtenerCartasPorId(@PathVariable("tipo") String tipo){
        return ResponseEntity.ok(cartaService.obtenerCartasPorTipo(tipo));
    }

    // Obtener cartas por partida
    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<CartaDto>> obtenerCartasPorPartida(@PathVariable("partidaId") Long partidaId) {
        return ResponseEntity.ok(cartaService.obtenerCartasPorPartida(partidaId));
    }

    // Obtener cartas por jugador
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<CartaDto>> obtenerCartasPorJugador(@PathVariable("jugadorId") Long jugadorId) {
        return ResponseEntity.ok(cartaService.obtenerCartasPorJugador(jugadorId));
    }

    // Obtener cartas sin repartir en una partida
    @GetMapping("/sin-repartir/{partidaId}")
    public ResponseEntity<List<CartaDto>> obtenerCartasSinRepartir(@PathVariable("partidaId") Long partidaId) {
        return ResponseEntity.ok(cartaService.obtenerCartasSinRepartir(partidaId));
    }

    // Actualizar una carta existente
    @PutMapping("/{id}")
    public ResponseEntity<CartaDto> actualizarCarta(
            @PathVariable("id") Long id,
            @Valid @RequestBody CartaDto dto) {
        return ResponseEntity.ok(cartaService.actualizarCarta(id, dto));
    }

    // Eliminar una carta por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarta(@PathVariable("id") Long id) {
        cartaService.eliminarCarta(id);
        return ResponseEntity.noContent().build();
    }
}