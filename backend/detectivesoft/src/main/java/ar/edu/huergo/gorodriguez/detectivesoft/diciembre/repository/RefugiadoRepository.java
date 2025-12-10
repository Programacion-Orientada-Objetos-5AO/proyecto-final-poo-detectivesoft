package ar.edu.huergo.gorodriguez.detectivesoft.diciembre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.entity.Refugiado;

import java.util.List;

@Repository
public interface RefugiadoRepository extends JpaRepository<Refugiado, Long> {

    List<Refugiado> findByTipo(String tipo);

    List<Refugiado> findByAdoptado(Boolean adoptado);
}
