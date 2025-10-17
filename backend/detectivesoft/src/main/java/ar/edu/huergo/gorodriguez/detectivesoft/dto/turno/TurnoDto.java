package ar.edu.huergo.gorodriguez.detectivesoft.dto.turno;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurnoDto {

    private Long id;
    private Long partidaId;
    private Long jugadorId;

    private int numeroTurno;  
    private boolean activo;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
