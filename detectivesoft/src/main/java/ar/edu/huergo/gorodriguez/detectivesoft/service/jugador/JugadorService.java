package ar.edu.huergo.gorodriguez.detectivesoft.service.jugador;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JugadorService {
    private final JugadorRepository jugadorRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador getJugadorById(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jugador con id " + id + " no encontrado"));
    }

    public Jugador getJugadorByUsername(String username) {
        return jugadorRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Jugador con username " + username + " no encontrado"));
    }

    public Jugador registrar(Jugador jugador, String password, String verificacionPassword) {
        if (password == null || verificacionPassword == null) {
            throw new IllegalArgumentException("Las contraseñas no pueden ser null");
        }
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (jugadorRepository.existsByUsername(jugador.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }
        if (jugadorRepository.existsByEmail(jugador.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        jugador.setPassword(passwordEncoder.encode(password));
        return jugadorRepository.save(jugador);
    }

    public Jugador actualizarJugador(Long id, Jugador datos) {
        Jugador existente = getJugadorById(id);
        existente.setEmail(datos.getEmail());
        existente.setUsername(datos.getUsername());
        if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(datos.getPassword()));
        }
        return jugadorRepository.save(existente);
    }

    public void eliminarJugador(Long id) {
        Jugador jugador = getJugadorById(id);
        jugadorRepository.delete(jugador);
    }
}
