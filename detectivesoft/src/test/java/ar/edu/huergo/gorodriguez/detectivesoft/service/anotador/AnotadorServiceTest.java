package ar.edu.huergo.gorodriguez.detectivesoft.service.anotador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador.AnotadorMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador.AnotadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AnotadorService")
class AnotadorServiceTest {

    @Mock
    private AnotadorRepository anotadorRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private AnotadorMapper anotadorMapper;

    @InjectMocks
    private AnotadorServiceImpl anotadorService;

    private Jugador jugador;
    private Partida partida;
    private Anotador anotador;
    private AnotadorDto anotadorDto;

    @BeforeEach
    void setUp() {
        jugador = new Jugador();
        jugador.setId(1L);
        jugador.setUsername("testUser");

        partida = new Partida();
        partida.setId(1L);
        partida.setCodigo("TEST123");

        anotador = new Anotador();
        anotador.setId(1L);
        anotador.setJugador(jugador);
        anotador.setPartida(partida);
        anotador.setCartasDescartadas(Arrays.asList(1L, 2L));

        anotadorDto = AnotadorDto.builder()
                .id(1L)
                .jugadorId(1L)
                .partidaId(1L)
                .cartasDescartadas(Arrays.asList(1L, 2L))
                .build();
    }

    @Test
    @DisplayName("Debería crear anotador correctamente")
    void deberiaCrearAnotadorCorrectamente() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(anotadorRepository.save(any(Anotador.class))).thenReturn(anotador);
        when(anotadorMapper.toDto(anotador)).thenReturn(anotadorDto);

        AnotadorDto resultado = anotadorService.crearAnotador(1L, 1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(jugadorRepository, times(1)).findById(1L);
        verify(partidaRepository, times(1)).findById(1L);
        verify(anotadorRepository, times(1)).save(any(Anotador.class));
        verify(anotadorMapper, times(1)).toDto(anotador);
    }

    @Test
    @DisplayName("Debería lanzar excepción si jugador no existe al crear")
    void deberiaLanzarExcepcionSiJugadorNoExisteAlCrear() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> anotadorService.crearAnotador(1L, 1L));
    }

    @Test
    @DisplayName("Debería lanzar excepción si partida no existe al crear")
    void deberiaLanzarExcepcionSiPartidaNoExisteAlCrear() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> anotadorService.crearAnotador(1L, 1L));
    }

    @Test
    @DisplayName("Debería obtener anotador por ID")
    void deberiaObtenerAnotadorPorId() {
        when(anotadorRepository.findById(1L)).thenReturn(Optional.of(anotador));
        when(anotadorMapper.toDto(anotador)).thenReturn(anotadorDto);

        AnotadorDto resultado = anotadorService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(anotadorRepository, times(1)).findById(1L);
        verify(anotadorMapper, times(1)).toDto(anotador);
    }

    @Test
    @DisplayName("Debería lanzar excepción si anotador no existe")
    void deberiaLanzarExcepcionSiAnotadorNoExiste() {
        when(anotadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> anotadorService.obtenerPorId(1L));
    }

    @Test
    @DisplayName("Debería listar anotadores por partida")
    void deberiaListarAnotadoresPorPartida() {
        List<Anotador> anotadores = Arrays.asList(anotador);
        List<AnotadorDto> anotadoresDto = Arrays.asList(anotadorDto);

        when(anotadorRepository.findByPartidaId(1L)).thenReturn(anotadores);
        when(anotadorMapper.toDtoList(anotadores)).thenReturn(anotadoresDto);

        List<AnotadorDto> resultado = anotadorService.listarPorPartida(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(anotadorRepository, times(1)).findByPartidaId(1L);
        verify(anotadorMapper, times(1)).toDtoList(anotadores);
    }

    @Test
    @DisplayName("Debería actualizar cartas descartadas correctamente")
    void deberiaActualizarCartasDescartadas() {
        List<Long> nuevasCartas = Arrays.asList(3L, 4L);
        Anotador anotadorActualizado = new Anotador();
        anotadorActualizado.setId(1L);
        anotadorActualizado.setCartasDescartadas(nuevasCartas);

        when(anotadorRepository.findById(1L)).thenReturn(Optional.of(anotador));
        when(anotadorRepository.save(any(Anotador.class))).thenReturn(anotadorActualizado);
        when(anotadorMapper.toDto(anotadorActualizado)).thenReturn(
                AnotadorDto.builder()
                        .id(1L)
                        .cartasDescartadas(nuevasCartas)
                        .build());

        AnotadorDto resultado = anotadorService.actualizarCartasDescartadas(1L, nuevasCartas);

        assertNotNull(resultado);
        assertEquals(nuevasCartas, resultado.getCartasDescartadas());
        verify(anotadorRepository, times(1)).findById(1L);
        verify(anotadorRepository, times(1)).save(any(Anotador.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción si anotador no existe al actualizar")
    void deberiaLanzarExcepcionSiAnotadorNoExisteAlActualizar() {
        when(anotadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> anotadorService.actualizarCartasDescartadas(1L, Arrays.asList(1L)));
    }

    @Test
    @DisplayName("Debería eliminar anotador correctamente")
    void deberiaEliminarAnotador() {
        when(anotadorRepository.existsById(1L)).thenReturn(true);

        anotadorService.eliminarAnotador(1L);

        verify(anotadorRepository, times(1)).existsById(1L);
        verify(anotadorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción si anotador no existe al eliminar")
    void deberiaLanzarExcepcionSiAnotadorNoExisteAlEliminar() {
        when(anotadorRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> anotadorService.eliminarAnotador(1L));
    }
}
