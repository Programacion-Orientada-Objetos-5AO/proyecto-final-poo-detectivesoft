package ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcusacionDto {
    private Long id;
    private Long partidaId;
    private Long jugadorId;
    private Long personajeId; 
    private Long armaId;      
    private Long habitacionId;
    private LocalDateTime fecha;
    private Boolean correcta;
}
