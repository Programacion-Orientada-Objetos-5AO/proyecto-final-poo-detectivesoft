package ar.edu.huergo.gorodriguez.detectivesoft.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo; // código para invitar jugadores

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jugador> jugadores = new ArrayList<>();

    @Column(nullable = false)
    private int maxJugadores = 6; // valor por defecto

    @Column(nullable = false)
    private int recuentoJugadores = 0;

    // Métodos utilitarios
    public void agregarJugador(Jugador jugador) {
        if (recuentoJugadores >= maxJugadores) {
            throw new IllegalStateException("La partida ya alcanzó el máximo de jugadores (" + maxJugadores + ")");
        }
        jugadores.add(jugador);
        jugador.setPartida(this);
        recuentoJugadores = jugadores.size();
    }

    public void removerJugador(Jugador jugador) {
        jugadores.remove(jugador);
        jugador.setPartida(null);
        recuentoJugadores = jugadores.size();
    }

    public enum EstadoPartida {
        PENDIENTE,
        EN_CURSO,
        FINALIZADA
    }
} 