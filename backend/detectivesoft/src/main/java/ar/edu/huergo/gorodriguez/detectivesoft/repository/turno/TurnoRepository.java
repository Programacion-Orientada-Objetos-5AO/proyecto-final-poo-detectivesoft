package ar.edu.huergo.gorodriguez.detectivesoft.repository.turno;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByPartida(Partida partida);
    List<Turno> findByJugador(Jugador jugador);
}
