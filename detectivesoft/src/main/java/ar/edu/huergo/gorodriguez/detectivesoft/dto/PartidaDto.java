package ar.edu.huergo.gorodriguez.detectivesoft.dto;

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
    private String codigo; // código para invitar jugadores
    private String estado; // estado de la partida: PENDIENTE, EN_CURSO, FINALIZADA
    private LocalDateTime fechaCreacion; // fecha y hora de creación de la partida
    private int maxJugadores; // máximo de jugadores permitidos en la partida
    private int recuentoJugadores; // número actual de jugadores en la partida
    private List<JugadorResumenDto> jugadores; // lista de jugadores en la partida
}
