package ar.edu.huergo.gorodriguez.detectivesoft.controller.cartas;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.cartas.CartaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.security.MensajeDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.cartas.CartaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cartas")
public class CartaController {

    private final CartaService cartaService;

    // Crear carta
    @PostMapping
    public ResponseEntity<CartaDto> crearCarta(@RequestBody CartaDto dto) {
        CartaDto nuevaCarta = cartaService.crearCarta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCarta);
    }

    // Obtener cartas por partida
    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<?> obtenerPorPartida(@PathVariable Long partidaId) {
        List<CartaDto> cartas = cartaService.obtenerCartasPorPartida(partidaId);
        if (cartas.isEmpty()) {
            return ResponseEntity.ok(new MensajeDto("No hay cartas en esta partida."));
        }
        return ResponseEntity.ok(cartas);
    }

    // Obtener cartas por jugador
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<?> obtenerPorJugador(@PathVariable Long jugadorId) {
        List<CartaDto> cartas = cartaService.obtenerCartasPorJugador(jugadorId);
        if (cartas.isEmpty()) {
            return ResponseEntity.ok(new MensajeDto("Este jugador no tiene cartas."));
        }
        return ResponseEntity.ok(cartas);
    }

    // Obtener cartas sin repartir en una partida
    @GetMapping("/partida/{partidaId}/sin-repartir")
    public ResponseEntity<?> obtenerSinRepartir(@PathVariable Long partidaId) {
        List<CartaDto> cartas = cartaService.obtenerCartasSinRepartir(partidaId);
        if (cartas.isEmpty()) {
            return ResponseEntity.ok(new MensajeDto("Todas las cartas ya fueron repartidas."));
        }
        return ResponseEntity.ok(cartas);
    }

    // Eliminar carta
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDto> eliminarCarta(@PathVariable Long id) {
        cartaService.eliminarCarta(id);
        return ResponseEntity.ok(new MensajeDto("Carta eliminada correctamente."));
    }
}
