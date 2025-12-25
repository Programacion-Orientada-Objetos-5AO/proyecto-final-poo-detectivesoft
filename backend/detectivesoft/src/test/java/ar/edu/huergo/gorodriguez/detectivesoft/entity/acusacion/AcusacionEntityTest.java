package ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
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

@DisplayName("Tests de Validación - Entidad Acusacion")
class AcusacionEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería fallar validación si faltan campos obligatorios")
    void deberiaFallarSiFaltanCampos() {
        Acusacion acusacion = new Acusacion();
        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("partida")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("jugador")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("personaje")));
    }

    @Test
    @DisplayName("Debería pasar validación con datos válidos")
    void deberiaPasarValidacionConDatosValidos() {
        Partida partida = new Partida();
        Jugador jugador = new Jugador();
        Carta personaje = new Carta();
        Carta arma = new Carta();
        Carta habitacion = new Carta();

        Acusacion acusacion = new Acusacion(1L, partida, jugador, personaje, arma, habitacion, LocalDateTime.now(), false);
        Set<ConstraintViolation<Acusacion>> violaciones = validator.validate(acusacion);
        assertTrue(violaciones.isEmpty());
    }
}
