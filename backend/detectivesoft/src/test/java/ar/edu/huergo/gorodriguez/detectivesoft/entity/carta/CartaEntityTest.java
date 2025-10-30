package ar.edu.huergo.gorodriguez.detectivesoft.entity.carta;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Carta")
class CartaEntityTest {

    private Validator validator;
    private Carta carta;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        carta = new Carta();
        carta.setNombre("Cuchillo");
        carta.setTipo(Carta.TipoCarta.ARMA);
        carta.setImagen("cuchillo.png");
    }

    @Test
    @DisplayName("Debería pasar validación con datos correctos")
    void deberiaSerValida() {
        Set<ConstraintViolation<Carta>> violaciones = validator.validate(carta);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar si el nombre es nulo o vacío")
    void deberiaFallarSiNombreEsInvalido() {
        carta.setNombre(" ");
        Set<ConstraintViolation<Carta>> violaciones = validator.validate(carta);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar si el tipo es nulo")
    void deberiaFallarSiTipoEsNulo() {
        carta.setTipo(null);
        Set<ConstraintViolation<Carta>> violaciones = validator.validate(carta);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("tipo")));
    }
}
