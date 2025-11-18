package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.mapper;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto.ResponseDto;
import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.entity.Calculadora;

@Component
public class CalculadoraMapper {
    public Calculadora toEntity(RequestDto dto){
        if (dto == null) return null;
        Calculadora calculadora = new Calculadora();

        calculadora.setId(dto.getId());
        calculadora.setParametro1(dto.getParametro1());
        calculadora.setParametro2(dto.getParametro2());
        calculadora.setOperacion(dto.getOperacion());
        calculadora.setResultado(null);
        
        return calculadora;
    }

    public ResponseDto toDto(Calculadora calculadora, Double Resultado){
        if (calculadora == null) return null;
        return ResponseDto.builder()
        .id(calculadora.getId())
        .parametro1(calculadora.getParametro1())
        .parametro2(calculadora.getParametro2())
        .operacion(calculadora.getOperacion())
        .resultado(Resultado)
        .build();
    }

    
}
