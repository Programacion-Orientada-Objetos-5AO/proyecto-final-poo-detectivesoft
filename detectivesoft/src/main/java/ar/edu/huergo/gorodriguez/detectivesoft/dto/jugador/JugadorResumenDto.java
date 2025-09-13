package ar.edu.huergo.gorodriguez.detectivesoft.dto.jugador;

import lombok.Data; 
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JugadorResumenDto {
    private Long id;
    private String nombre;
}
