package ar.edu.huergo.gorodriguez.detectivesoft.service.turno;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.turno.TurnoMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.turno.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        jugador = new Jugador();
        jugador.setId(1L);

        turno = new Turno(1L, partida, jugador, 1, true, null, null);
        turnoDto = new TurnoDto();
        turnoDto.setId(1L);
        turnoDto.setNumeroTurno(1);
        turnoDto.setActivo(true);
    }

    @Test
    @DisplayName("Debería crear turno correctamente")
    void deberiaCrearTurno() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(turnoRepository.save(any())).thenReturn(turno);
        when(turnoMapper.toDto(any())).thenReturn(turnoDto);

        TurnoDto resultado = turnoService.crearTurno(1L, 1L, 1);

        assertEquals(1, resultado.getNumeroTurno());
        verify(turnoRepository).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción si partida no existe al crear turno")
    void deberiaFallarSiPartidaNoExiste() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> turnoService.crearTurno(1L, 1L, 1));
    }

    @Test
    @DisplayName("Debería obtener turno por ID")
    void deberiaObtenerTurnoPorId() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoMapper.toDto(turno)).thenReturn(turnoDto);

        TurnoDto resultado = turnoService.obtenerTurnoPorId(1L);

        assertEquals(1L, resultado.getId());
        verify(turnoRepository).findById(1L);
    }

    @Test
    @DisplayName("Debería finalizar turno correctamente")
    void deberiaFinalizarTurno() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoRepository.save(any())).thenReturn(turno);
        when(turnoMapper.toDto(any())).thenReturn(turnoDto);

        TurnoDto resultado = turnoService.finalizarTurno(1L);
        assertEquals(1L, resultado.getId());

        assertFalse(turno.isActivo());
        assertNotNull(turno.getFechaFin());
        verify(turnoRepository).save(turno);
    }

    @Test
    @DisplayName("Debería eliminar turno correctamente")
    void deberiaEliminarTurno() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        turnoService.eliminarTurno(1L);
        verify(turnoRepository).delete(turno);
    }

    @Test
    @DisplayName("Debería lanzar excepción al eliminar turno inexistente")
    void deberiaFallarAlEliminarTurnoInexistente() {
        when(turnoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> turnoService.eliminarTurno(99L));
    }
}
