package ar.edu.huergo.gorodriguez.detectivesoft.dto.carta;

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
    private String tipo;   // "PERSONAJE", "ARMA" o "HABITACION"
    private String imagen; // URL o direccion de la imagen

    private Long partidaId; // id de la partida a la que pertenece la carta
    private Long jugadorId; // id del jugador que posee la carta (puede ser null si no está asignada)
}
