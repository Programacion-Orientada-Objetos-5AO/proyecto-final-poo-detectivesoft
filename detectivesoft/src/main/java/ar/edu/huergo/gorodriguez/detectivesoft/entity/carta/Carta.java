package ar.edu.huergo.gorodriguez.detectivesoft.entity.carta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartas")
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private TipoCarta tipo;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    public enum TipoCarta {
        PERSONAJE, ARMA, HABITACION
    }
}
