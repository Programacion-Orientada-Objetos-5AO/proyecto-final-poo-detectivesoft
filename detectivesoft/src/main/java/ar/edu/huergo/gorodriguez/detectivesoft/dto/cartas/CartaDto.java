package ar.edu.huergo.gorodriguez.detectivesoft.dto.cartas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaDto {
    private Long id;
    private String nombre;
    private String tipo;
    private Long partidaId;
    private Long jugadorId;
}
