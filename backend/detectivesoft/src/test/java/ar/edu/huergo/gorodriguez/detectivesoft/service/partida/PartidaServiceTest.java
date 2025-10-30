package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida.PartidaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.turno.TurnoRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.service.anotador.AnotadorService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PartidaService")
class PartidaServiceTest {

    @Mock private PartidaRepository partidaRepository;
    @Mock private JugadorRepository jugadorRepository;
    @Mock private CartaRepository cartaRepository;
    @Mock private TurnoRepository turnoRepository;
    @Mock private AnotadorService anotadorService;
    @Mock private PartidaMapper partidaMapper;

    @InjectMocks
    private PartidaServiceImpl partidaService;

    private Jugador jugador;
    private Partida partida;

    @BeforeEach
    void setUp() {
        jugador = new Jugador("test@mail.com", "jugador", "1234");
        jugador.setId(1L);

        partida = new Partida();
        partida.setId(1L);
        partida.setCodigo(UUID.randomUUID().toString().substring(0, 6));
        partida.setEstado(EstadoPartida.PENDIENTE);
        partida.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    @DisplayName("Debería crear partida correctamente")
    void deberiaCrearPartida() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        when(partidaMapper.toDto(any(Partida.class))).thenReturn(new PartidaDto());

        PartidaDto resultado = partidaService.crearPartida(1L);

        assertNotNull(resultado);
        verify(partidaRepository, times(1)).save(any(Partida.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción si jugador no existe al crear partida")
    void deberiaFallarSiJugadorNoExiste() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> partidaService.crearPartida(1L));
    }

    @Test
    @DisplayName("Debería obtener partida por ID")
    void deberiaObtenerPartidaPorId() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(partidaMapper.toDto(partida)).thenReturn(new PartidaDto());

        PartidaDto resultado = partidaService.obtenerPartidaPorId(1L);

        assertNotNull(resultado);
        verify(partidaRepository).findById(1L);
    }

    @Test
    @DisplayName("Debería eliminar partida correctamente")
    void deberiaEliminarPartida() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));

        partidaService.eliminarPartida(1L);

        verify(partidaRepository).delete(partida);
    }

    @Test
    @DisplayName("Debería lanzar excepción si no existe partida al eliminar")
    void deberiaFallarAlEliminarPartidaInexistente() {
        when(partidaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> partidaService.eliminarPartida(99L));
    }
}
