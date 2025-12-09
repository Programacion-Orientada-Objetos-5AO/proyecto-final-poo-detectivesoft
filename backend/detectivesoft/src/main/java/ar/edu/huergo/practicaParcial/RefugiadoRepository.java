package ar.edu.huergo.practicaParcial;

import ar.edu.huergo.practicaParcial.Refugiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefugiadoRepository extends JpaRepository<Refugiado, Long> {

    List<Refugiado> findByTipo(String tipo);

    List<Refugiado> findByAdoptado(Boolean adoptado);
}
