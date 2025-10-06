package ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;

@DataJpaTest
@DisplayName("Tests de Integración - JugadorRepository")
class JugadorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JugadorRepository jugadorRepository;

    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        jugador1 = new Jugador("test1@gmail.com", "player1", "password1");
        jugador2 = new Jugador("test2@gmail.com", "player2", "password2");

        entityManager.persistAndFlush(jugador1);
        entityManager.persistAndFlush(jugador2);
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar jugador por email")
    void deberiaEncontrarJugadorPorEmail() {
        Optional<Jugador> resultado = jugadorRepository.findByEmail("test1@gmail.com");
        assertTrue(resultado.isPresent());
        assertEquals("player1", resultado.get().getUsername());
    }

    @Test
    @DisplayName("Debería encontrar jugador por username")
    void deberiaEncontrarJugadorPorUsername() {
        Optional<Jugador> resultado = jugadorRepository.findByUsername("player2");
        assertTrue(resultado.isPresent());
        assertEquals("test2@gmail.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Debería verificar existencia por email y username")
    void deberiaVerificarExistenciaPorEmailYUsername() {
        assertTrue(jugadorRepository.existsByEmail("test1@gmail.com"));
        assertTrue(jugadorRepository.existsByUsername("player2"));
        assertFalse(jugadorRepository.existsByEmail("inexistente@gmail.com"));
        assertFalse(jugadorRepository.existsByUsername("noexiste"));
    }

    @Test
    @DisplayName("Debería guardar y recuperar jugador correctamente")
    void deberiaGuardarYRecuperarJugador() {
        Jugador nuevo = new Jugador("nuevo@gmail.com", "nuevoUser", "pass123");
        Jugador guardado = jugadorRepository.save(nuevo);
        entityManager.flush();
        entityManager.clear();

        Optional<Jugador> recuperado = jugadorRepository.findById(guardado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals("nuevoUser", recuperado.get().getUsername());
    }
}
