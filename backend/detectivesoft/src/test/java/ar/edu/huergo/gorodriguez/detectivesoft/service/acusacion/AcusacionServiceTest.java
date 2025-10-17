package ar.edu.huergo.gorodriguez.detectivesoft.service.acusacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.acusacion.AcusacionMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion.AcusacionRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AcusacionService")
class AcusacionServiceTest {

    @Mock
    private AcusacionRepository acusacionRepository;

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private AcusacionMapper acusacionMapper;

    @InjectMocks
    private AcusacionServiceImpl acusacionService;

    private Partida partida;
    private Jugador jugador;
    private Acusacion acusacion;
    private AcusacionDto acusacionDto;

    @BeforeEach
    void setUp() {
        partida = new Partida();
        partida.setId(1L);
        partida.setCodigo("ABC123");
        partida.setEstado(EstadoPartida.EN_CURSO);

        Jugador jugador = new Jugador("test@mail.com", "userTest", "1234");
        jugador.setId(1L);
        jugador.setPartida(null);
        jugador.setPartidasJugadas(0);
        jugador.setPartidasGanadas(0);

        acusacion = new Acusacion();
        acusacion.setId(1L);
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);
        acusacion.setFecha(LocalDateTime.now());
        acusacion.setCorrecta(false);

        acusacionDto = AcusacionDto.builder()
                .id(1L)
                .partidaId(1L)
                .jugadorId(1L)
                .fecha(LocalDateTime.now())
                .correcta(false)
                .build();
    }

    @Test
    @DisplayName("Debería crear acusacion correctamente")
    void deberiaCrearAcusacionCorrectamente() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(acusacionMapper.toEntity(acusacionDto)).thenReturn(acusacion);
        when(acusacionRepository.save(acusacion)).thenReturn(acusacion);
        when(acusacionMapper.toDto(acusacion)).thenReturn(acusacionDto);

        AcusacionDto resultado = acusacionService.crearAcusacion(acusacionDto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(acusacionRepository, times(1)).save(acusacion);
    }

    @Test
    @DisplayName("Debería lanzar excepción si partida no existe al crear acusacion")
    void deberiaLanzarExcepcionSiPartidaNoExisteAlCrearAcusacion() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> acusacionService.crearAcusacion(acusacionDto));
    }

    @Test
    @DisplayName("Debería lanzar excepción si jugador no existe al crear acusacion")
    void deberiaLanzarExcepcionSiJugadorNoExisteAlCrearAcusacion() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> acusacionService.crearAcusacion(acusacionDto));
    }

    @Test
    @DisplayName("Debería obtener todas las acusaciones")
    void deberiaObtenerTodasLasAcusaciones() {
        when(acusacionRepository.findAll()).thenReturn(Arrays.asList(acusacion));
        when(acusacionMapper.toDtoList(anyList())).thenReturn(Arrays.asList(acusacionDto));

        List<AcusacionDto> resultado = acusacionService.obtenerTodas();

        assertEquals(1, resultado.size());
        verify(acusacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener acusaciones por partida")
    void deberiaObtenerAcusacionesPorPartida() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(acusacionRepository.findByPartida(partida)).thenReturn(Arrays.asList(acusacion));
        when(acusacionMapper.toDtoList(anyList())).thenReturn(Arrays.asList(acusacionDto));

        List<AcusacionDto> resultado = acusacionService.obtenerPorPartida(1L);

        assertEquals(1, resultado.size());
        verify(acusacionRepository, times(1)).findByPartida(partida);
    }

    @Test
    @DisplayName("Debería obtener acusaciones por jugador")
    void deberiaObtenerAcusacionesPorJugador() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(acusacionRepository.findByJugador(jugador)).thenReturn(Arrays.asList(acusacion));
        when(acusacionMapper.toDtoList(anyList())).thenReturn(Arrays.asList(acusacionDto));

        List<AcusacionDto> resultado = acusacionService.obtenerPorJugador(1L);

        assertEquals(1, resultado.size());
        verify(acusacionRepository, times(1)).findByJugador(jugador);
    }

    @Test
    @DisplayName("Debería verificar existencia de acusacion correcta")
    void deberiaVerificarExistenciaDeAcusacionCorrecta() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(acusacionRepository.findByPartidaAndCorrectaTrue(partida)).thenReturn(Arrays.asList(acusacion));

        boolean existe = acusacionService.existeAcusacionCorrecta(1L);

        assertTrue(existe);
        verify(acusacionRepository, times(1)).findByPartidaAndCorrectaTrue(partida);
    }

    @Test
    @DisplayName("Debería retornar false si no existe acusacion correcta")
    void deberiaRetornarFalseSiNoExisteAcusacionCorrecta() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(acusacionRepository.findByPartidaAndCorrectaTrue(partida)).thenReturn(Collections.emptyList());

        boolean existe = acusacionService.existeAcusacionCorrecta(1L);

        assertFalse(existe);
    }

    @Test
    @DisplayName("Debería eliminar acusacion correctamente")
    void deberiaEliminarAcusacionCorrectamente() {
        when(acusacionRepository.existsById(1L)).thenReturn(true);

        acusacionService.eliminarAcusacion(1L);

        verify(acusacionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción si acusacion no existe al eliminar")
    void deberiaLanzarExcepcionSiAcusacionNoExisteAlEliminar() {
        when(acusacionRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> acusacionService.eliminarAcusacion(1L));
    }
}
