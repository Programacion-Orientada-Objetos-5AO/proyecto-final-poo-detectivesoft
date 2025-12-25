package ar.edu.huergo.gorodriguez.detectivesoft.parcial.mapper;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto.RequestDto;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto.ResponseDto;
import ar.edu.huergo.gorodriguez.detectivesoft.parcial.entity.Calculadora;

@Component
public class CalculadoraMapper {

    public Calculadora toEntity(RequestDto dto){
        if (dto == null) return null;

        Calculadora calculadora = new Calculadora();
        calculadora.setId(dto.getId());
        calculadora.setParametro1(dto.getParametro1());
        calculadora.setParametro2(dto.getParametro2());
        calculadora.setOperacion(dto.getOperacion());
        return calculadora;
    }

    public ResponseDto toDto(Calculadora calculadora){
        if (calculadora == null) return null;

        return ResponseDto.builder()
            .id(calculadora.getId())
            .parametro1(calculadora.getParametro1())
            .parametro2(calculadora.getParametro2())
            .operacion(calculadora.getOperacion())
            .resultado(calculadora.getResultado())
            .build();
    }
}
