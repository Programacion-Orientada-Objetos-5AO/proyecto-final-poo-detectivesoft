package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.ResponseDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.service.CalculadoraService;

import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculadora")
public class CalculadoraController {

    private final CalculadoraService calculadoraService;

    @PostMapping
    public ResponseEntity<ResponseDto> realizarOperacion(@Valid @RequestBody RequestDto dto){
        ResponseDto response = calculadoraService.realizarOperacion(dto);
        return ResponseEntity.ok(response);
    }
}
