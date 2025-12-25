package ar.edu.huergo.gorodriguez.detectivesoft.entity.partida;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

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
        partida.setFechaCreacion(LocalDateTime.now());
        partida.setMaxJugadores(6);
    }

    @Test
    @DisplayName("Debería fallar validación si el código es nulo")
    void deberiaFallarConCodigoNulo() {
        partida.setCodigo(null);

        Set<ConstraintViolation<Partida>> violaciones = validator.validate(partida);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("codigo")));
    }

    @Test
    @DisplayName("Debería agregar y remover jugadores correctamente")
    void deberiaAgregarYRemoverJugadores() {
        Jugador j1 = new Jugador("test@mail.com", "player", "1234");

        partida.agregarJugador(j1);
        assertEquals(1, partida.getJugadores().size());
        assertEquals(partida, j1.getPartida());

        partida.removerJugador(j1);
        assertEquals(0, partida.getJugadores().size());
        assertNull(j1.getPartida());
    }

    @Test
    @DisplayName("Debería lanzar excepción si se supera el máximo de jugadores")
    void deberiaLanzarExcepcionSiSuperaMaximo() {
        partida.setMaxJugadores(2);
        partida.agregarJugador(new Jugador("a@mail.com", "A", "1"));
        partida.agregarJugador(new Jugador("b@mail.com", "B", "2"));

        assertThrows(IllegalStateException.class, () -> partida.agregarJugador(
                new Jugador("c@mail.com", "C", "3")));
    }
}
