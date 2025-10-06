package ar.edu.huergo.gorodriguez.detectivesoft.entity.partida;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests de Validación y Lógica - Entidad Partida")
class PartidaEntityTest {

    private Validator validator;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        partida = new Partida();
        partida.setCodigo("ABC123");
        partida.setEstado(Partida.EstadoPartida.PENDIENTE);
        partida.setMaxJugadores(2);
    }

    // ---------- VALIDACIONES ----------

    @Test
    @DisplayName("Debería fallar validación con código nulo")
    void deberiaFallarConCodigoNulo() {
        partida.setCodigo(null);

        Set<ConstraintViolation<Partida>> violaciones = validator.validate(partida);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("codigo")));
    }

    @Test
    @DisplayName("Debería fallar validación con estado nulo")
    void deberiaFallarConEstadoNulo() {
        partida.setEstado(null);

        Set<ConstraintViolation<Partida>> violaciones = validator.validate(partida);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estado")));
    }

    // ---------- LÓGICA ----------

    @Test
    @DisplayName("Debería agregar jugador e incrementar recuento")
    void deberiaAgregarJugadorEIncrementarRecuento() {
        Jugador jugador1 = new Jugador("gonza@mail.com", "gonza", "1234");

        partida.agregarJugador(jugador1);

        assertEquals(1, partida.getRecuentoJugadores());
        assertTrue(partida.getJugadores().contains(jugador1));
    }

    @Test
    @DisplayName("Debería lanzar excepción si se supera el máximo de jugadores")
    void deberiaLanzarExcepcionSiSuperaMaximo() {
        partida.setMaxJugadores(1);

        Jugador jugador1 = new Jugador("gonza@mail.com", "gonza", "1234");
        Jugador jugador2 = new Jugador("thiago@mail.com", "thiago", "5678");

        partida.agregarJugador(jugador1);

        assertThrows(IllegalStateException.class, () -> partida.agregarJugador(jugador2));
    }

    @Test
    @DisplayName("Debería remover jugador y decrementar recuento")
    void deberiaRemoverJugadorYDecrementarRecuento() {
        Jugador jugador1 = new Jugador("gonza@mail.com", "gonza", "1234");
        partida.agregarJugador(jugador1);

        partida.removerJugador(jugador1);

        assertEquals(0, partida.getRecuentoJugadores());
        assertFalse(partida.getJugadores().contains(jugador1));
    }
}
