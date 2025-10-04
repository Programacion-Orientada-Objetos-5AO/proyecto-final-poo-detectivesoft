package ar.edu.huergo.gorodriguez.detectivesoft.service.turno;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.turno.TurnoMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.turno.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - TurnoService")
class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private TurnoServiceImpl turnoService;

    private Partida partida;
    private Jugador jugador;
    private Turno turno;
    private TurnoDto turnoDto;

    @BeforeEach
    void setUp() {
        partida = new Partida();
        partida.setId(1L);
        partida.setCodigo("ABC123");
        partida.setEstado(EstadoPartida.EN_CURSO);

        jugador = new Jugador(1L, "test@test.com", "usuarioTest", "1234", null, 0, 0);

        turno = new Turno();
        turno.setId(1L);
        turno.setPartida(partida);
        turno.setJugador(jugador);
        turno.setNumeroTurno(1);
        turno.setActivo(true);
        turno.setFechaInicio(LocalDateTime.now());

        turnoDto = TurnoDto.builder()
                .id(1L)
                .partidaId(1L)
                .jugadorId(1L)
                .numeroTurno(1)
                .activo(true)
                .fechaInicio(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Debería crear turno correctamente")
    void deberiaCrearTurnoCorrectamente() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        when(turnoMapper.toDto(any(Turno.class))).thenReturn(turnoDto);

        TurnoDto resultado = turnoService.crearTurno(1L, 1L, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getNumeroTurno());
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción si partida no existe al crear turno")
    void deberiaLanzarExcepcionSiPartidaNoExisteAlCrearTurno() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> turnoService.crearTurno(1L, 1L, 1));
    }

    @Test
    @DisplayName("Debería lanzar excepción si jugador no existe al crear turno")
    void deberiaLanzarExcepcionSiJugadorNoExisteAlCrearTurno() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> turnoService.crearTurno(1L, 1L, 1));
    }

    @Test
    @DisplayName("Debería obtener turno por ID")
    void deberiaObtenerTurnoPorId() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoMapper.toDto(turno)).thenReturn(turnoDto);

        TurnoDto resultado = turnoService.obtenerTurnoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debería lanzar excepción si turno no existe")
    void deberiaLanzarExcepcionSiTurnoNoExiste() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> turnoService.obtenerTurnoPorId(1L));
    }

    @Test
    @DisplayName("Debería obtener todos los turnos")
    void deberiaObtenerTodosLosTurnos() {
        when(turnoRepository.findAll()).thenReturn(Arrays.asList(turno));
        when(turnoMapper.toDtoList(anyList())).thenReturn(Arrays.asList(turnoDto));

        List<TurnoDto> resultado = turnoService.obtenerTodosLosTurnos();

        assertEquals(1, resultado.size());
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener turnos por partida")
    void deberiaObtenerTurnosPorPartida() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(turnoRepository.findByPartida(partida)).thenReturn(Arrays.asList(turno));
        when(turnoMapper.toDtoList(anyList())).thenReturn(Arrays.asList(turnoDto));

        List<TurnoDto> resultado = turnoService.obtenerTurnosPorPartida(1L);

        assertEquals(1, resultado.size());
        verify(turnoRepository, times(1)).findByPartida(partida);
    }

    @Test
    @DisplayName("Debería obtener turnos por jugador")
    void deberiaObtenerTurnosPorJugador() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(turnoRepository.findByJugador(jugador)).thenReturn(Arrays.asList(turno));
        when(turnoMapper.toDtoList(anyList())).thenReturn(Arrays.asList(turnoDto));

        List<TurnoDto> resultado = turnoService.obtenerTurnosPorJugador(1L);

        assertEquals(1, resultado.size());
        verify(turnoRepository, times(1)).findByJugador(jugador);
    }

    @Test
    @DisplayName("Debería finalizar turno correctamente")
    void deberiaFinalizarTurnoCorrectamente() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        when(turnoMapper.toDto(any(Turno.class))).thenReturn(turnoDto);

        TurnoDto resultado = turnoService.finalizarTurno(1L);

        assertNotNull(resultado);
        verify(turnoRepository, times(1)).save(turno);
    }

    @Test
    @DisplayName("Debería eliminar turno correctamente")
    void deberiaEliminarTurnoCorrectamente() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        turnoService.eliminarTurno(1L);

        verify(turnoRepository, times(1)).delete(turno);
    }

    @Test
    @DisplayName("Debería actualizar turno correctamente")
    void deberiaActualizarTurnoCorrectamente() {
        TurnoDto dtoActualizado = TurnoDto.builder()
                .numeroTurno(2)
                .activo(false)
                .build();

        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        when(turnoMapper.toDto(any(Turno.class))).thenReturn(dtoActualizado);

        TurnoDto resultado = turnoService.actualizarTurno(1L, dtoActualizado);

        assertEquals(2, resultado.getNumeroTurno());
        assertFalse(resultado.isActivo());
        verify(turnoRepository, times(1)).save(turno);
    }
}
