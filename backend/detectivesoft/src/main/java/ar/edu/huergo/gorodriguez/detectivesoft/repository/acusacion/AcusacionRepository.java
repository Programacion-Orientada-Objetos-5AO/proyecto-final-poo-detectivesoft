package ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;

@Repository
public interface AcusacionRepository extends JpaRepository<Acusacion, Long> {

    List<Acusacion> findByPartida(Partida partida);

    List<Acusacion> findByJugador(Jugador jugador);

    List<Acusacion> findByPartidaAndJugador(Partida partida, Jugador jugador);

    List<Acusacion> findByPartidaAndCorrectaTrue(Partida partida);
}
