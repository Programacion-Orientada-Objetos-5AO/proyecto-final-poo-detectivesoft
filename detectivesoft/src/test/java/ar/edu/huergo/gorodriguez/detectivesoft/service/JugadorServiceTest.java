package ar.edu.huergo.gorodriguez.detectivesoft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.service.jugador.JugadorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - JugadorService")
class JugadorServiceTest {

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private JugadorService jugadorService;

    private Jugador jugadorEjemplo;

    @BeforeEach
    void setUp() {
        jugadorEjemplo = new Jugador(1L, "test@test.com", "usuarioTest", "1234", null, 0, 0);
    }

    @Test
    @DisplayName("Debería obtener todos los jugadores")
    void deberiaObtenerTodosLosJugadores() {
        when(jugadorRepository.findAll()).thenReturn(Arrays.asList(jugadorEjemplo));

        List<Jugador> resultado = jugadorService.getAllJugadores();

        assertEquals(1, resultado.size());
        assertEquals("usuarioTest", resultado.get(0).getUsername());
        verify(jugadorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener jugador por ID existente")
    void deberiaObtenerJugadorPorId() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugadorEjemplo));

        Jugador resultado = jugadorService.getJugadorById(1L);

        assertNotNull(resultado);
        assertEquals("usuarioTest", resultado.getUsername());
    }

    @Test
    @DisplayName("Debería lanzar excepción si el jugador no existe")
    void deberiaLanzarExcepcionSiJugadorNoExiste() {
        when(jugadorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> jugadorService.getJugadorById(999L));
    }

    @Test
    @DisplayName("Debería registrar jugador correctamente")
    void deberiaRegistrarJugadorCorrectamente() {
        when(jugadorRepository.existsByUsername(jugadorEjemplo.getUsername())).thenReturn(false);
        when(jugadorRepository.existsByEmail(jugadorEjemplo.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorEjemplo);

        Jugador resultado = jugadorService.registrar(jugadorEjemplo, "1234", "1234");

        assertEquals("encodedPassword", resultado.getPassword());
        verify(jugadorRepository, times(1)).save(jugadorEjemplo);
    }

    @Test
    @DisplayName("Debería lanzar excepción si las contraseñas no coinciden")
    void deberiaLanzarExcepcionSiPasswordsNoCoinciden() {
        assertThrows(IllegalArgumentException.class,
                () -> jugadorService.registrar(jugadorEjemplo, "1234", "5678"));
    }

    @Test
    @DisplayName("Debería lanzar excepción si el username ya existe")
    void deberiaLanzarExcepcionSiUsernameYaExiste() {
        when(jugadorRepository.existsByUsername(jugadorEjemplo.getUsername())).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> jugadorService.registrar(jugadorEjemplo, "1234", "1234"));
    }

    @Test
    @DisplayName("Debería actualizar jugador correctamente")
    void deberiaActualizarJugador() {
        Jugador actualizado = new Jugador(1L, "nuevo@test.com", "nuevoUser", "nuevaPass", null, 0, 0);

        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugadorEjemplo));
        when(passwordEncoder.encode("nuevaPass")).thenReturn("encodedNuevaPass");
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(actualizado);

        Jugador resultado = jugadorService.actualizarJugador(1L, actualizado);

        assertEquals("nuevoUser", resultado.getUsername());
        assertEquals("encodedNuevaPass", resultado.getPassword());
    }

    @Test
    @DisplayName("Debería eliminar jugador correctamente")
    void deberiaEliminarJugador() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugadorEjemplo));

        jugadorService.eliminarJugador(1L);

        verify(jugadorRepository, times(1)).delete(jugadorEjemplo);
    }
}
