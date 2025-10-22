package ar.edu.huergo.gorodriguez.detectivesoft.dto.carta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaResumenDto {
    private Long id;
    private String nombre;
    private String tipo;
}
