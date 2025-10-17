package ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;

public interface AnotadorRepository extends JpaRepository<Anotador, Long> {

    List<Anotador> findByPartidaId(Long partidaId);

    Optional<Anotador> findByJugadorIdAndPartidaId(Long jugadorId, Long partidaId);
}
