package ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByEmail(String email);
    Optional<Jugador> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}