package ar.edu.huergo.gorodriguez.detectivesoft.service.anotador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador.AnotadorMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador.AnotadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private AnotadorDto dto;

    @BeforeEach
    void setUp() {
        jugador = new Jugador();
        jugador.setId(1L);
        jugador.setEmail("test@mail.com");
        jugador.setUsername("player");

        partida = new Partida();
        partida.setId(2L);
        partida.setCodigo("ABC123");

        anotador = Anotador.builder()
                .id(10L)
                .jugador(jugador)
                .partida(partida)
                .cartasDescartadas(List.of(1L, 2L))
                .build();

        dto = new AnotadorDto();
        dto.setId(10L);
    }

    @Test
    @DisplayName("Debería crear un anotador correctamente")
    void deberiaCrearAnotador() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(partidaRepository.findById(2L)).thenReturn(Optional.of(partida));
        when(anotadorRepository.save(any())).thenReturn(anotador);
        when(anotadorMapper.toDto(any())).thenReturn(dto);

        AnotadorDto resultado = anotadorService.crearAnotador(1L, 2L);

        assertNotNull(resultado);
        verify(anotadorRepository).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción si jugador no existe al crear anotador")
    void deberiaFallarSiJugadorNoExiste() {
        when(jugadorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> anotadorService.crearAnotador(1L, 2L));
    }

    @Test
    @DisplayName("Debería obtener anotador por ID")
    void deberiaObtenerPorId() {
        when(anotadorRepository.findById(10L)).thenReturn(Optional.of(anotador));
        when(anotadorMapper.toDto(any())).thenReturn(dto);

        AnotadorDto resultado = anotadorService.obtenerPorId(10L);

        assertEquals(10L, resultado.getId());
    }

    @Test
    @DisplayName("Debería listar anotadores por partida")
    void deberiaListarPorPartida() {
        when(anotadorRepository.findByPartidaId(2L)).thenReturn(List.of(anotador));
        when(anotadorMapper.toDtoList(any())).thenReturn(List.of(dto));

        List<AnotadorDto> lista = anotadorService.listarPorPartida(2L);

        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Debería eliminar anotador correctamente")
    void deberiaEliminarAnotador() {
        when(anotadorRepository.existsById(10L)).thenReturn(true);
        anotadorService.eliminarAnotador(10L);
        verify(anotadorRepository).deleteById(10L);
    }

    @Test
    @DisplayName("Debería lanzar excepción si anotador no existe al eliminar")
    void deberiaFallarSiNoExisteAlEliminar() {
        when(anotadorRepository.existsById(10L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> anotadorService.eliminarAnotador(10L));
    }
}
