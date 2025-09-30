package ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;

@Repository
public interface AcusacionRepository extends JpaRepository<Acusacion, Long> {

    // Buscar todas las acusaciones de una partida
    List<Acusacion> findByPartida(Partida partida);

    // Buscar todas las acusaciones de un jugador
    List<Acusacion> findByJugador(Jugador jugador);

    // Buscar acusaciones de un jugador en una partida
    List<Acusacion> findByPartidaAndJugador(Partida partida, Jugador jugador);

    // Buscar si ya hubo alguna acusación correcta en la partida
    List<Acusacion> findByPartidaAndCorrectaTrue(Partida partida);
}
