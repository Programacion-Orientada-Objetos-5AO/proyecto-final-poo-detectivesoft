package ar.edu.huergo.gorodriguez.detectivesoft.entity.turno;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests de Validación y Lógica - Entidad Turno")
class TurnoEntityTest {

    private Validator validator;
    private Partida partida;
    private Jugador jugador;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        partida = new Partida();
        partida.setId(1L);
        partida.setCodigo("ABC123");

        jugador = new Jugador();
        jugador.setId(1L);
        jugador.setEmail("test@test.com");
        jugador.setUsername("testuser");
    }

    // ---------- VALIDACIONES ----------

    @Test
    @DisplayName("Debería fallar validación con partida nula")
    void deberiaFallarConPartidaNula() {
        Turno turno = new Turno();
        turno.setJugador(jugador);
        turno.setNumeroTurno(1);
        turno.setActivo(true);

        Set<ConstraintViolation<Turno>> violaciones = validator.validate(turno);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("partida")));
    }

    @Test
    @DisplayName("Debería fallar validación con jugador nulo")
    void deberiaFallarConJugadorNulo() {
        Turno turno = new Turno();
        turno.setPartida(partida);
        turno.setNumeroTurno(1);
        turno.setActivo(true);

        Set<ConstraintViolation<Turno>> violaciones = validator.validate(turno);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("jugador")));
    }

    // ---------- LÓGICA ----------

    @Test
    @DisplayName("Debería crear un turno válido correctamente")
    void deberiaCrearTurnoValido() {
        Turno turno = new Turno();
        turno.setPartida(partida);
        turno.setJugador(jugador);
        turno.setNumeroTurno(1);
        turno.setActivo(true);

        Set<ConstraintViolation<Turno>> violaciones = validator.validate(turno);

        assertTrue(violaciones.isEmpty());
        assertEquals(partida, turno.getPartida());
        assertEquals(jugador, turno.getJugador());
        assertEquals(1, turno.getNumeroTurno());
        assertTrue(turno.isActivo());
        assertNotNull(turno.getFechaInicio());
        assertNull(turno.getFechaFin());
    }

    @Test
    @DisplayName("Debería finalizar turno correctamente")
    void deberiaFinalizarTurnoCorrectamente() {
        Turno turno = new Turno();
        turno.setPartida(partida);
        turno.setJugador(jugador);
        turno.setNumeroTurno(1);
        turno.setActivo(true);

        turno.finalizarTurno();

        assertFalse(turno.isActivo());
        assertNotNull(turno.getFechaFin());
    }
}
