package ar.edu.huergo.practicaParcial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NuevoRefugiadoDto {
    private String nombre;
    private String tipo;
    private Integer edad;
}
