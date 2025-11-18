package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.entity.Calculadora;

public interface CalculadoraRepository extends JpaRepository<Calculadora, Long>{
    List<Calculadora> findByTipoOperacion(String tipoOperacion);
}
