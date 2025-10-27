package ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador;

import java.util.List;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaResumenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnotadorDto {
    private Long id;
    private Long jugadorId;
    private String jugadorNombre;
    private Long partidaId;
    private List<CartaResumenDto> cartasDescartadas;
}
