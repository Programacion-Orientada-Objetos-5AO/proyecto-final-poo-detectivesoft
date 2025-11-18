package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    Long id;
    String operacion;
    Double parametro1;
    Double parametro2;
}
