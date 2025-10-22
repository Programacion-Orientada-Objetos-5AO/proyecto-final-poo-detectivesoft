package ar.edu.huergo.gorodriguez.detectivesoft.entity.turno;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación y Lógica - Entidad Turno")
class TurnoEntityTest {

    private Validator validator;
    private Turno turno;
    private Partida partida;
    private Jugador jugador;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        partida = new Partida();
        jugador = new Jugador();

        turno = new Turno();
        turno.setPartida(partida);
        turno.setJugador(jugador);
        turno.setNumeroTurno(1);
        turno.setActivo(true);
    }

    @Test
    @DisplayName("Debería pasar validación con datos correctos")
    void deberiaSerValido() {
        Set<ConstraintViolation<Turno>> violaciones = validator.validate(turno);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar si partida es nula")
    void deberiaFallarSiPartidaEsNula() {
        turno.setPartida(null);
        Set<ConstraintViolation<Turno>> violaciones = validator.validate(turno);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("partida")));
    }

    @Test
    @DisplayName("Debería fallar si jugador es nulo")
    void deberiaFallarSiJugadorEsNulo() {
        turno.setJugador(null);
        Set<ConstraintViolation<Turno>> violaciones = validator.validate(turno);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("jugador")));
    }

    @Test
    @DisplayName("Debería finalizar turno correctamente")
    void deberiaFinalizarTurno() {
        turno.finalizarTurno();

        assertFalse(turno.isActivo());
        assertNotNull(turno.getFechaFin());
    }
}
