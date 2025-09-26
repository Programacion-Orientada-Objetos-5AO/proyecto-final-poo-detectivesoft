package ar.edu.huergo.gorodriguez.detectivesoft.repository.carta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;

public interface CartaRepository extends JpaRepository<Carta, Long> {

    List<Carta> findByPartidaId(Long partidaId);

    List<Carta> findByJugadorId(Long jugadorId);

    List<Carta> findByPartidaIdAndJugadorIsNull(Long partidaId);
}
