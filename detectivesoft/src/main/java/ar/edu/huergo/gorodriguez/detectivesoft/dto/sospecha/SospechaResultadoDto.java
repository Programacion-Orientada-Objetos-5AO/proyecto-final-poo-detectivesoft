package ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SospechaResultadoDto {
    private String jugadorQueMostro;
    private String cartaMostrada;
    private String mensaje;
}
