package ar.edu.huergo.gorodriguez.detectivesoft.entity.turno;

import java.time.LocalDateTime;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación: la partida a la que pertenece el turno
    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    // Relación: el jugador al que le corresponde este turno
    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @Column(nullable = false)
    private int numeroTurno; // número secuencial del turno dentro de la partida

    @Column(nullable = false)
    private boolean activo = false; // indica si el turno está en curso

    @Column(nullable = false)
    private LocalDateTime fechaInicio = LocalDateTime.now();

    private LocalDateTime fechaFin;

    // Método para finalizar el turno
    public void finalizarTurno() {
        this.activo = false;
        this.fechaFin = LocalDateTime.now();
    }
}
