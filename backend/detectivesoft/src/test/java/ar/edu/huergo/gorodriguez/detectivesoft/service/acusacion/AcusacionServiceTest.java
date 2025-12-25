package ar.edu.huergo.gorodriguez.detectivesoft.service.acusacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.acusacion.AcusacionMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion.AcusacionRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AcusacionService")
class AcusacionServiceTest {

    @Mock private AcusacionRepository acusacionRepository;
    @Mock private PartidaRepository partidaRepository;
    @Mock private JugadorRepository jugadorRepository;
    @Mock private CartaRepository cartaRepository;
    @Mock private AcusacionMapper acusacionMapper;

    @InjectMocks private AcusacionServiceImpl acusacionService;

    private Jugador jugador;
    private Partida partida;
    private Turno turno;
    private Carta arma, habitacion, personaje;
    private AcusacionDto dto;

    @BeforeEach
    void setUp() {
        jugador = new Jugador();
        jugador.setId(1L);
        jugador.setUsername("testUser");
        jugador.setEmail("test@mail.com");

        partida = new Partida();
        partida.setId(10L);
        partida.setEstado(EstadoPartida.PENDIENTE);
        turno = new Turno();
        turno.setJugador(jugador);
        partida.setTurnoActual(turno);

        arma = new Carta(1L, "Cuchillo", Carta.TipoCarta.ARMA, null, null, null);
        habitacion = new Carta(2L, "Biblioteca", Carta.TipoCarta.HABITACION, null, null, null);
        personaje = new Carta(3L, "Sr. Verde", Carta.TipoCarta.PERSONAJE, null, null, null);

        dto = new AcusacionDto();
        dto.setPartidaId(10L);
        dto.setArmaId(1L);
        dto.setHabitacionId(2L);
        dto.setPersonajeId(3L);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test@mail.com");
        when(auth.isAuthenticated()).thenReturn(true);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    @Test
    @DisplayName("Debería crear una acusación correcta y finalizar la partida")
    void deberiaCrearAcusacionCorrectaYFinalizarPartida() {
        partida.setCartaCulpableArma(arma);
        partida.setCartaCulpableHabitacion(habitacion);
        partida.setCartaCulpablePersonaje(personaje);

        when(jugadorRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(jugador));
        when(partidaRepository.findById(10L)).thenReturn(Optional.of(partida));
        when(cartaRepository.findById(1L)).thenReturn(Optional.of(arma));
        when(cartaRepository.findById(2L)).thenReturn(Optional.of(habitacion));
        when(cartaRepository.findById(3L)).thenReturn(Optional.of(personaje));
        when(acusacionRepository.save(any(Acusacion.class))).thenAnswer(inv -> inv.getArgument(0));

        AcusacionDto resultado = acusacionService.crearAcusacion(dto);

        verify(acusacionRepository, times(1)).save(any());
        assertNotNull(resultado);
        assertEquals(EstadoPartida.FINALIZADA, partida.getEstado());
    }

    @Test
    @DisplayName("Debería lanzar excepción si no hay jugador autenticado")
    void deberiaLanzarExcepcionSiNoHayAuth() {
        SecurityContextHolder.clearContext();
        assertThrows(IllegalStateException.class, () -> acusacionService.crearAcusacion(dto));
    }

    @Test
    @DisplayName("Debería lanzar excepción si el jugador no es el del turno actual")
    void deberiaLanzarExcepcionSiNoEsSuTurno() {
        Jugador otro = new Jugador();
        otro.setId(99L);
        turno.setJugador(otro);

        when(jugadorRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(jugador));
        when(partidaRepository.findById(10L)).thenReturn(Optional.of(partida));

        assertThrows(IllegalStateException.class, () -> acusacionService.crearAcusacion(dto));
    }
}
