package ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha;

import lombok.Data;

@Data
public class SospechaRequest {
    private Long partidaId;
    private Long personajeId;
    private Long armaId;
    private Long habitacionId;
}
