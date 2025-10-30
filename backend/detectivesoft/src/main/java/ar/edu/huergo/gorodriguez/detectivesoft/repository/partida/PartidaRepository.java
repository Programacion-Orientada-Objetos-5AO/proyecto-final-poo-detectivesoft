package ar.edu.huergo.gorodriguez.detectivesoft.repository.partida;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    Optional<Partida> findByCodigo(String codigo);

    List<Partida> findByEstado(String estado);

    List<Partida> findByFechaCreacionAfter(java.time.LocalDateTime fecha);

    boolean existsByCodigo(String codigo);
}
