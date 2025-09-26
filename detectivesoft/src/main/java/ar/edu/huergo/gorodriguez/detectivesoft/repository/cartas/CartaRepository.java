package ar.edu.huergo.gorodriguez.detectivesoft.repository.cartas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.cartas.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.cartas.Carta.TipoCarta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

public interface CartaRepository extends JpaRepository<Carta, Long> {
    List<Carta> findByPartida(Partida partida);
    List<Carta> findByJugador(Jugador jugador);
    List<Carta> findByTipo(TipoCarta tipo);
}
