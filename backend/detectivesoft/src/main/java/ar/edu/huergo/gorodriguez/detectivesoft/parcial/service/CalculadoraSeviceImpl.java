package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.service;

import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.ResponseDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.entity.Calculadora;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.mapper.CalculadoraMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.repository.CalculadoraRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculadoraSeviceImpl implements CalculadoraService{
        
    private final CalculadoraMapper calculadoraMapper;
    private final CalculadoraRepository calculadoraRepository;

    public ResponseDto realizarOperacion(RequestDto requestDto){

        Calculadora calculadora = calculadoraMapper.toEntity(requestDto);

        String operacion = calculadora.getOperacion();

        Double resultado = calculadora.getResultado();

        if (operacion == "+"){
            resultado = (calculadora.getParametro1() + calculadora.getParametro2());
        }
        if (operacion == "-"){
            resultado = (calculadora.getParametro1() - calculadora.getParametro2());
        }
        if (operacion == "*"){
            resultado = (calculadora.getParametro1() * calculadora.getParametro2());
        }
        if (operacion == "/"){
            resultado = (calculadora.getParametro1() / calculadora.getParametro2());
        }

        return calculadoraMapper.toDto(calculadora, resultado);
    }
}
