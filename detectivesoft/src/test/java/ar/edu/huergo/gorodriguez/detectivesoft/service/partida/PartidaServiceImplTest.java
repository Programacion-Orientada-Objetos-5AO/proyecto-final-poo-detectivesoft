package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

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

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida.PartidaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PartidaServiceImpl")
class PartidaServiceImplTest {

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private PartidaMapper partidaMapper;

    @InjectMocks
    private PartidaServiceImpl partidaService;

    private Jugador jugador;
    private Partida partida;
    private PartidaDto partidaDto;

    @BeforeEach
    void setUp() {
        Jugador jugador = new Jugador("test@mail.com", "userTest", "1234");
        jugador.setId(1L);
        jugador.setPartida(null);
        jugador.setPartidasJugadas(0);
        jugador.setPartidasGanadas(0);
        partida = new Partida();
        partida.setId(1L);
        partida.setCodigo("ABC123");
        partida.setEstado(EstadoPartida.PENDIENTE);
        partida.setMaxJugadores(2);
        partida.getJugadores().add(jugador);

        partidaDto = PartidaDto.builder()
                .id(1L)
                .codigo("ABC123")
                .estado("PENDIENTE")
                .maxJugadores(2)
                .recuentoJugadores(1)
                .build();
    }

    @Test
    @DisplayName("Debería crear partida correctamente")
    void deberiaCrearPartida() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        when(partidaMapper.toDto(any(Partida.class))).thenReturn(partidaDto);

        PartidaDto resultado = partidaService.crearPartida(1L);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    @DisplayName("Debería lanzar excepción si jugador no existe al crear partida")
    void deberiaLanzarExcepcionSiJugadorNoExisteAlCrear() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> partidaService.crearPartida(1L));
    }

    @Test
    @DisplayName("Debería unirse a partida correctamente")
    void deberiaUnirseAPartida() {
        Jugador nuevo = new Jugador("test@mail.com", "userTest", "1234");
        nuevo.setId(1L);
        nuevo.setPartida(null);
        nuevo.setPartidasJugadas(0);
        nuevo.setPartidasGanadas(0);

        when(partidaRepository.findByCodigo("ABC123")).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(2L)).thenReturn(Optional.of(nuevo));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        when(partidaMapper.toDto(any(Partida.class))).thenReturn(partidaDto);

        PartidaDto resultado = partidaService.unirseAPartida("ABC123", 2L);

        assertNotNull(resultado);
        verify(partidaRepository, times(1)).save(partida);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la partida está llena")
    void deberiaLanzarExcepcionSiPartidaLlena() {
        Jugador otro = new Jugador("test@mail.com", "userTest", "1234");
        otro.setId(1L);
        otro.setPartida(null);
        otro.setPartidasJugadas(0);
        otro.setPartidasGanadas(0);

        partida.getJugadores().add(otro);

        when(partidaRepository.findByCodigo("ABC123")).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(3L)).thenReturn(Optional.of(new Jugador()));

        assertThrows(RuntimeException.class, () -> partidaService.unirseAPartida("ABC123", 3L));
    }

    @Test
    @DisplayName("Debería listar partidas")
    void deberiaListarPartidas() {
        when(partidaRepository.findAll()).thenReturn(Arrays.asList(partida));
        when(partidaMapper.toDto(any(Partida.class))).thenReturn(partidaDto);

        List<PartidaDto> resultado = partidaService.listarPartidas();

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debería eliminar jugador de partida")
    void deberiaEliminarJugadorDePartida() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        when(partidaMapper.toDto(any(Partida.class))).thenReturn(partidaDto);

        PartidaDto resultado = partidaService.eliminarJugadorDePartida(1L, 1L);

        assertNotNull(resultado);
        verify(partidaRepository, times(1)).save(partida);
    }
}