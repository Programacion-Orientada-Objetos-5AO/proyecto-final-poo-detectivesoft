package ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Anotador")
class AnotadorEntityTest {

    private Validator validator;
    private Anotador anotador;
    private Jugador jugador;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        jugador = new Jugador();
        partida = new Partida();

        anotador = Anotador.builder()
                .jugador(jugador)
                .partida(partida)
                .build();
    }

    @Test
    @DisplayName("Debería pasar validación con datos válidos")
    void deberiaSerValido() {
        Set<ConstraintViolation<Anotador>> violaciones = validator.validate(anotador);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar si jugador es nulo")
    void deberiaFallarSiJugadorEsNulo() {
        anotador.setJugador(null);
        Set<ConstraintViolation<Anotador>> violaciones = validator.validate(anotador);
        assertFalse(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar si partida es nula")
    void deberiaFallarSiPartidaEsNula() {
        anotador.setPartida(null);
        Set<ConstraintViolation<Anotador>> violaciones = validator.validate(anotador);
        assertFalse(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería permitir agregar cartas descartadas")
    void deberiaAgregarCartasDescartadas() {
        Carta carta1 = new Carta();
        Carta carta2 = new Carta();
        anotador.getCartasDescartadas().add(carta1);
        anotador.getCartasDescartadas().add(carta2);

        assertEquals(2, anotador.getCartasDescartadas().size());
        assertTrue(anotador.getCartasDescartadas().contains(carta1));
        assertTrue(anotador.getCartasDescartadas().contains(carta2));
    }
}
