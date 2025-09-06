package ar.edu.huergo.gorodriguez.detectivesoft.repository.partida;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    // Buscar partida por código único
    Optional<Partida> findByCodigo(String codigo);

    // Buscar todas las partidas en cierto estado (ej: PENDIENTE, EN_CURSO, FINALIZADA)
    List<Partida> findByEstado(String estado);

    // Buscar partidas creadas por fecha (ejemplo: partidas recientes)
    List<Partida> findByFechaCreacionAfter(java.time.LocalDateTime fecha);

    // Ver si existe una partida con cierto código
    boolean existsByCodigo(String codigo);
}
