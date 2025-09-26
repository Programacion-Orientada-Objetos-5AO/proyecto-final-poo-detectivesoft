package ar.edu.huergo.gorodriguez.detectivesoft.dto.cartas;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaResumenDto {
    private Long id;
    private String nombre;
}
