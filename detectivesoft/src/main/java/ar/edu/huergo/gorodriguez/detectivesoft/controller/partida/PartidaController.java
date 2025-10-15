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
import java.util.Map;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.security.MensajeDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.service.partida.PartidaService;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;
    private final PartidaRepository partidaRepository;

    // Crear partida
    @PostMapping("/crear/{creadorId}")
    public ResponseEntity<?> crearPartida(@PathVariable("creadorId") Long creadorId) {
        PartidaDto partida = partidaService.crearPartida(creadorId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MensajeDto("Partida creada correctamente. Código: " + partida.getCodigo()));
    }

    // Unirse a partida
    @PostMapping("/unirse/{codigo}")
    public ResponseEntity<MensajeDto> unirseAPartida(
            @PathVariable("codigo") String codigo ) 
            {
        partidaService.unirseAPartida(codigo);
        return ResponseEntity.ok(new MensajeDto("Te uniste correctamente a la partida."));
    }

    // Listar partidas
    @GetMapping
    public ResponseEntity<?> listarPartidas() {
        List<PartidaDto> partidas = partidaService.listarPartidas();
        if (partidas.isEmpty()) {
            return ResponseEntity.ok(new MensajeDto("No hay partidas disponibles."));
        }
        return ResponseEntity.ok(partidas);
    }

    // Obtener partida por ID
    @GetMapping("/{id}")
    public ResponseEntity<PartidaDto> obtenerPartidaPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(partidaService.obtenerPartidaPorId(id));
    }

    // Eliminar partida por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDto> eliminarPartida(@PathVariable("id") Long id) {
        partidaService.eliminarPartida(id);
        return ResponseEntity.ok(new MensajeDto("Partida eliminada correctamente."));
    }

    // Eliminar jugador de una partida
    @DeleteMapping("/{partidaId}/jugadores/{jugadorId}")
    public ResponseEntity<MensajeDto> eliminarJugadorDePartida(
            @PathVariable("partidaId") Long partidaId,
            @PathVariable("jugadorId") Long jugadorId) {
        partidaService.eliminarJugadorDePartida(partidaId, jugadorId);
        return ResponseEntity.ok(new MensajeDto("Jugador eliminado correctamente de la partida."));
    }

    // Iniciar partida
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<MensajeDto> iniciarPartida(@PathVariable("id") Long id) {
        partidaService.iniciarPartida(id);
        return ResponseEntity.ok(new MensajeDto("Partida iniciada y cartas repartidas correctamente."));
    }


    @GetMapping("/{partidaId}/turno-actual")
    public ResponseEntity<?> obtenerTurnoActual(@PathVariable Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        
        if (partida.getTurnoActual() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new MensajeDto("La partida aún no tiene turno activo."));
        }

        var turno = partida.getTurnoActual();
        return ResponseEntity.ok(Map.of(
                "numeroTurno", turno.getNumeroTurno(),
                "jugadorId", turno.getJugador().getId(),
                "jugadorNombre", turno.getJugador().getUsername()
        ));
    }

}
