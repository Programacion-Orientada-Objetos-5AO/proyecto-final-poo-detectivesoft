package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.service;

import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.ResponseDto;

public interface CalculadoraService {
    ResponseDto realizarOperacion(RequestDto requestDto);
}
