package ar.edu.huergo.gorodriguez.detectivesoft.dto.partida;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.jugador.JugadorResumenDto;
import lombok.Data; 
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaDto {
    private Long id;
    private String codigo;
    private String estado;
    private LocalDateTime fechaCreacion;
    private int maxJugadores;
    private int recuentoJugadores;
    private List<JugadorResumenDto> jugadores;
    private Long turnoActualJugadorId;
    private String turnoActualJugadorNombre;

}
