package ar.edu.huergo.gorodriguez.detectivesoft.diciembre.controller;

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

import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.NuevoRefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.RefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.service.RefugiadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refugiados")
public class RefugiadoController {

    private final RefugiadoService refugiadoService;

    @PostMapping
    public ResponseEntity<RefugiadoDto> crearRefugiado(@Valid @RequestBody NuevoRefugiadoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(refugiadoService.crearRefugiado(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefugiadoDto> obtenerRefugiadoPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(refugiadoService.obtenerRefugiadoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<RefugiadoDto>> obtenerTodosLosRefugiados() {
        return ResponseEntity.ok(refugiadoService.obtenerTodosLosRefugiados());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<RefugiadoDto>> obtenerRefugiadosPorTipo(@PathVariable("tipo") String tipo){
        return ResponseEntity.ok(refugiadoService.obtenerRefugiadosPorTipo(tipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefugiadoDto> actualizarRefugiado(
            @PathVariable("id") Long id,
            @Valid @RequestBody RefugiadoDto dto) {
        return ResponseEntity.ok(refugiadoService.actualizarRefugiado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRefugiado(@PathVariable("id") Long id) {
        refugiadoService.eliminarRefugiado(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/{adoptado}")
    public ResponseEntity<RefugiadoDto> actualizarEstadoRefugiado(
            @PathVariable("id") Long id,
            @PathVariable("adoptado") Boolean adoptado) {
        return ResponseEntity.ok(refugiadoService.actualizarEstadoRefugiado(id, adoptado));
    }
}