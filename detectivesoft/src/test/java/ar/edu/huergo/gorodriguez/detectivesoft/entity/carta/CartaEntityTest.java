package ar.edu.huergo.gorodriguez.detectivesoft.entity.carta;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de Validación y Lógica - Entidad Carta")
class CartaEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ---------- VALIDACIONES ----------

    @Test
    @DisplayName("Debería fallar validación con nombre nulo o vacío")
    void deberiaFallarConNombreInvalido() {
        Carta carta = new Carta();
        carta.setNombre(""); // inválido
        carta.setTipo(Carta.TipoCarta.ARMA);

        Set<ConstraintViolation<Carta>> violaciones = validator.validate(carta);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación si tipo es nulo")
    void deberiaFallarConTipoNulo() {
        Carta carta = new Carta();
        carta.setNombre("Cuchillo");
        carta.setTipo(null); // inválido

        Set<ConstraintViolation<Carta>> violaciones = validator.validate(carta);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tipo")));
    }

    // ---------- LÓGICA BÁSICA ----------

    @Test
    @DisplayName("Debería crear una carta válida correctamente")
    void deberiaCrearCartaValida() {
        Carta carta = new Carta();
        carta.setNombre("Biblioteca");
        carta.setTipo(Carta.TipoCarta.HABITACION);

        Set<ConstraintViolation<Carta>> violaciones = validator.validate(carta);

        assertTrue(violaciones.isEmpty());
        assertEquals("Biblioteca", carta.getNombre());
        assertEquals(Carta.TipoCarta.HABITACION, carta.getTipo());
    }

    @Test
    @DisplayName("Enum TipoCarta debería contener los valores esperados")
    void deberiaTenerValoresEnumCorrectos() {
        assertEquals("PERSONAJE", Carta.TipoCarta.PERSONAJE.name());
        assertEquals("ARMA", Carta.TipoCarta.ARMA.name());
        assertEquals("HABITACION", Carta.TipoCarta.HABITACION.name());
    }
}
