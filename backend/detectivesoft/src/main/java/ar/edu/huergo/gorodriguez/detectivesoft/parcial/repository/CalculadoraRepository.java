package ar.edu.huergo.gorodriguez.detectivesoft.parcial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.gorodriguez.detectivesoft.parcial.entity.Calculadora;

@Repository
public interface CalculadoraRepository extends JpaRepository<Calculadora, Long>{
    List<Calculadora> findByOperacion(String tipoOperacion);
}
