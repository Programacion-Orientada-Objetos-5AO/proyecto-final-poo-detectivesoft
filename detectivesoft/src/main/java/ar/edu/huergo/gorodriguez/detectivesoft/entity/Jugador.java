package ar.edu.huergo.gorodriguez.detectivesoft.entity;

import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es requerido")
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    @Column(name = "partidas_jugadas", nullable = false)
    private int partidasJugadas = 0;

    @Column(name = "partidas_ganadas", nullable = false)
    private int partidasGanadas = 0;

    public Jugador(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.partidasJugadas = 0;
        this.partidasGanadas = 0;
    }

    public void incrementarPartidasJugadas() {
        this.partidasJugadas = Math.max(0, this.partidasJugadas + 1);
    }

    public void incrementarPartidasGanadas() {
        this.partidasGanadas++;
    }
}
