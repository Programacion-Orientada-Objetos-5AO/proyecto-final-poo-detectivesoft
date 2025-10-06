package ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
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

@DisplayName("Tests de Validación y Lógica - Entidad Acusacion")
class AcusacionEntityTest {

    private Validator validator;
    private Partida partida;
    private Jugador jugador;
    private Carta personaje;
    private Carta arma;
    private Carta habitacion;

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

        personaje = new Carta();
        personaje.setId(1L);
        personaje.setNombre("Profesor Plum");
        personaje.setTipo(Carta.TipoCarta.PERSONAJE);

        arma = new Carta();
        arma.setId(2L);
        arma.setNombre("Cuchillo");
        arma.setTipo(Carta.TipoCarta.ARMA);

        habitacion = new Carta();
        habitacion.setId(3L);
        habitacion.setNombre("Biblioteca");
        habitacion.setTipo(Carta.TipoCarta.HABITACION);
    }

    // ---------- VALIDACIONES ----------

    @Test
    @DisplayName("Debería fallar validación con partida nula")
    void deberiaFallarConPartidaNula() {
        Acusacion acusacion = new Acusacion();
        acusacion.setJugador(jugador);
        acusacion.setPersonaje(personaje);
        acusacion.setArma(arma);
        acusacion.setHabitacion(habitacion);

        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("partida")));
    }

    @Test
    @DisplayName("Debería fallar validación con jugador nulo")
    void deberiaFallarConJugadorNulo() {
        Acusacion acusacion = new Acusacion();
        acusacion.setPartida(partida);
        acusacion.setPersonaje(personaje);
        acusacion.setArma(arma);
        acusacion.setHabitacion(habitacion);

        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("jugador")));
    }

    @Test
    @DisplayName("Debería fallar validación con personaje nulo")
    void deberiaFallarConPersonajeNulo() {
        Acusacion acusacion = new Acusacion();
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);
        acusacion.setArma(arma);
        acusacion.setHabitacion(habitacion);

        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("personaje")));
    }

    @Test
    @DisplayName("Debería fallar validación con arma nula")
    void deberiaFallarConArmaNula() {
        Acusacion acusacion = new Acusacion();
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);
        acusacion.setPersonaje(personaje);
        acusacion.setHabitacion(habitacion);

        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("arma")));
    }

    @Test
    @DisplayName("Debería fallar validación con habitación nula")
    void deberiaFallarConHabitacionNula() {
        Acusacion acusacion = new Acusacion();
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);
        acusacion.setPersonaje(personaje);
        acusacion.setArma(arma);

        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("habitacion")));
    }

    // ---------- LÓGICA ----------

    @Test
    @DisplayName("Debería crear una acusación válida correctamente")
    void deberiaCrearAcusacionValida() {
        Acusacion acusacion = new Acusacion();
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);
        acusacion.setPersonaje(personaje);
        acusacion.setArma(arma);
        acusacion.setHabitacion(habitacion);
        acusacion.setCorrecta(true);

        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);

        assertTrue(violaciones.isEmpty());
        assertEquals(partida, acusacion.getPartida());
        assertEquals(jugador, acusacion.getJugador());
        assertEquals(personaje, acusacion.getPersonaje());
        assertEquals(arma, acusacion.getArma());
        assertEquals(habitacion, acusacion.getHabitacion());
        assertTrue(acusacion.getCorrecta());
        assertNotNull(acusacion.getFecha());
    }
}
