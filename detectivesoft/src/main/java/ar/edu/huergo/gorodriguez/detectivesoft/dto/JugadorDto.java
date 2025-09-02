package ar.edu.huergo.gorodriguez.detectivesoft.dto;

import lombok.Data; 
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JugadorDto {
    private Long id;
    private String nombre;
    private String email;
    private int partidasJugadas;
    private int partidasGanadas;
}
