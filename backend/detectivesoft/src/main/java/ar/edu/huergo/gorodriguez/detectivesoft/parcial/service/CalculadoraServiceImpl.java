package ar.edu.huergo.gorodriguez.detectivesoft.parcial.service;

import ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto.ResponseDto;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.entity.Calculadora;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.mapper.CalculadoraMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.repository.CalculadoraRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculadoraServiceImpl implements CalculadoraService {

    private final CalculadoraMapper calculadoraMapper;
    private final CalculadoraRepository calculadoraRepository;

    @Override
    public ResponseDto realizarOperacion(RequestDto requestDto) {

        Calculadora calculadora = calculadoraMapper.toEntity(requestDto);

        Double resultado;

        switch (calculadora.getOperacion()) {
            case "+":
                resultado = calculadora.getParametro1() + calculadora.getParametro2();
                break;
            case "-":
                resultado = calculadora.getParametro1() - calculadora.getParametro2();
                break;
            case "*":
                resultado = calculadora.getParametro1() * calculadora.getParametro2();
                break;
            case "/":
                resultado = calculadora.getParametro1() / calculadora.getParametro2();
                break;
            default:
                throw new IllegalArgumentException("Operación no válida");
        }

        // Seteo resultado en la entidad
        calculadora.setResultado(resultado);

        // Guardar en BD
        Calculadora guardada = calculadoraRepository.save(calculadora);

        // Transformar a DTO
        return calculadoraMapper.toDto(guardada);
    }
}


