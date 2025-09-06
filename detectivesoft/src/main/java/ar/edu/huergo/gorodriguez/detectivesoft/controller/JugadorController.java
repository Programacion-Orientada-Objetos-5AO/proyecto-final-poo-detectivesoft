package ar.edu.huergo.gorodriguez.detectivesoft.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.RegistrarDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.jugador.JugadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.JugadorMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jugadores")
public class JugadorController {
    private final JugadorService jugadorService;
    private final JugadorMapper jugadorMapper;
    
    @PostMapping("/registrar")
    public ResponseEntity<JugadorDto> registrarCliente(@Valid @RequestBody RegistrarDto registrarDTO) {
        Jugador jugador = jugadorMapper.toEntity(registrarDTO);
        Jugador nuevoJugador = jugadorService.registrar(jugador, registrarDTO.password(), registrarDTO.verificacionPassword());
        JugadorDto nuevoJugadorDTO = jugadorMapper.toDto(nuevoJugador);
        return ResponseEntity.ok(nuevoJugadorDTO);
    }

    @GetMapping
    public ResponseEntity<List<JugadorDto>> getAllJugadores() {
        List<Jugador> jugadores = jugadorService.getAllJugadores();
        List<JugadorDto> jugadorDTOs = jugadorMapper.toDtoList(jugadores);
        return ResponseEntity.ok(jugadorDTOs);
    }
}
