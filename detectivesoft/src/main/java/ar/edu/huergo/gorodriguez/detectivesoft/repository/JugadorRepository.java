package ar.edu.huergo.gorodriguez.detectivesoft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByUsername(String username);
    boolean existsByUsername(String username);
}