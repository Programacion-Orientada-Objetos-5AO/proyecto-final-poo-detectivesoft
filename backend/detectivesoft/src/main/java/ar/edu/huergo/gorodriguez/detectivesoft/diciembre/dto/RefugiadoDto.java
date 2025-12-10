package ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefugiadoDto {
    Long id;
    String nombre;
    String tipo;
    Integer edad;
    Boolean adoptado;
}
