package ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador;

import java.util.ArrayList;
import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "anotadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anotador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    @ElementCollection
    @CollectionTable(name = "anotador_cartas_descartadas", joinColumns = @JoinColumn(name = "anotador_id"))
    @Column(name = "carta_id")
    private List<Long> cartasDescartadas = new ArrayList<>();
}