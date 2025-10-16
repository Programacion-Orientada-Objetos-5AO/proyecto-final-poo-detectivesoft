package ar.edu.huergo.gorodriguez.detectivesoft.controller.sospecha;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha.SospechaRequest;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha.SospechaResultadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.sospecha.SospechaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sospechas")
public class SospechaController {

    private final SospechaService sospechaService;

    @PostMapping
    public ResponseEntity<SospechaResultadoDto> realizarSospecha(@RequestBody SospechaRequest dto) {
        SospechaResultadoDto resultado = sospechaService.resolverSospecha(dto);
        return ResponseEntity.ok(resultado);
    }
}
