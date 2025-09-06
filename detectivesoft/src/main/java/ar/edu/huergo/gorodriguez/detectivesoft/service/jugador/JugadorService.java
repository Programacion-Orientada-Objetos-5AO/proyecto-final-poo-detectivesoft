package ar.edu.huergo.gorodriguez.detectivesoft.service.jugador;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JugadorService {
    private final JugadorRepository jugadorRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador registrar(Jugador jugador, String password, String verificacionPassword) {
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (jugadorRepository.existsByUsername(jugador.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        jugador.setPassword(passwordEncoder.encode(password));
        return jugadorRepository.save(jugador);
    }
}
