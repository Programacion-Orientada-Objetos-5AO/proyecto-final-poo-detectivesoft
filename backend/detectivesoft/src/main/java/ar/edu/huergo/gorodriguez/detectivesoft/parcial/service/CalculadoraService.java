package ar.edu.huergo.gorodriguez.detectivesoft.parcial.service;

import ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto.ResponseDto;

public interface CalculadoraService {
    ResponseDto realizarOperacion(RequestDto requestDto);
}
