package ar.edu.huergo.gorodriguez.detectivesoft.repository.turno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    // Buscar todos los turnos de una partida
    List<Turno> findByPartidaId(Long partidaId);

    // Buscar todos los turnos de un jugador
    List<Turno> findByJugadorId(Long jugadorId);

    // Buscar el turno activo de un jugador en una partida específica
    Turno findByPartidaIdAndJugadorIdAndActivoTrue(Long partidaId, Long jugadorId);
}
