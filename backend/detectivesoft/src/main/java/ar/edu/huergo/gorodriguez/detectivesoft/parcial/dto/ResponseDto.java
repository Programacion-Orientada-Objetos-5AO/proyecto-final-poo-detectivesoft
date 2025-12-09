package ar.edu.huergo.gorodriguez.detectivesoft.parcial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {
    
    Long id;
    String operacion;
    Double parametro1;
    Double parametro2;
    Double resultado;

}
