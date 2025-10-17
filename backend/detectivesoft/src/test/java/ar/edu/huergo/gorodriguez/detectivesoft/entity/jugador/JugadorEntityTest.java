package ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests de Validación y Lógica - Entidad Jugador")
class JugadorEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ---------- VALIDACIONES ----------

    @Test
    @DisplayName("Debería fallar validación con email nulo")
    void deberiaFallarConEmailNulo() {
        Jugador jugador = new Jugador(null, "gonza", "1234");

        Set<ConstraintViolation<Jugador>> violaciones = validator.validate(jugador);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("requerido")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "usuarioMuyLargo123456"})
    @DisplayName("Debería fallar validación con usernames inválidos")
    void deberiaFallarConUsernamesInvalidos(String username) {
        Jugador jugador = new Jugador("gonza@mail.com", username, "1234");

        Set<ConstraintViolation<Jugador>> violaciones = validator.validate(jugador);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    @DisplayName("Debería fallar validación con password vacía")
    void deberiaFallarConPasswordVacia() {
        Jugador jugador = new Jugador("gonza@mail.com", "gonza", "");

        Set<ConstraintViolation<Jugador>> violaciones = validator.validate(jugador);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    // ---------- LÓGICA DE NEGOCIO ----------

    @Test
    @DisplayName("Debería incrementar partidas jugadas correctamente")
    void deberiaIncrementarPartidasJugadas() {
        Jugador jugador = new Jugador("gonza@mail.com", "gonza", "1234");
        jugador.incrementarPartidasJugadas();

        assertEquals(1, jugador.getPartidasJugadas());
    }

    @Test
    @DisplayName("Debería incrementar partidas ganadas correctamente")
    void deberiaIncrementarPartidasGanadas() {
        Jugador jugador = new Jugador("gonza@mail.com", "gonza", "1234");
        jugador.incrementarPartidasGanadas();

        assertEquals(1, jugador.getPartidasGanadas());
    }
}