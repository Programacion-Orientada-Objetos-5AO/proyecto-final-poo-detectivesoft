package ar.edu.huergo.gorodriguez.detectivesoft.service.carta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.carta.CartaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - CartaService")
class CartaServiceTest {

    @Mock
    private CartaRepository cartaRepository;

    @Mock
    private CartaMapper cartaMapper;

    @InjectMocks
    private CartaServiceImpl cartaService;

    private Carta carta;
    private CartaDto cartaDto;

    @BeforeEach
    void setUp() {
        carta = new Carta(1L, "Cuchillo", Carta.TipoCarta.ARMA, "cuchillo.png", null, null);
        cartaDto = new CartaDto();
        cartaDto.setId(1L);
        cartaDto.setNombre("Cuchillo");
        cartaDto.setTipo("ARMA");
        cartaDto.setImagen("cuchillo.png");
    }

    @Test
    @DisplayName("Debería crear carta correctamente")
    void deberiaCrearCarta() {
        when(cartaMapper.toEntity(any())).thenReturn(carta);
        when(cartaRepository.save(any())).thenReturn(carta);
        when(cartaMapper.toDto(any())).thenReturn(cartaDto);

        CartaDto resultado = cartaService.crearCarta(cartaDto);

        assertNotNull(resultado);
        assertEquals("Cuchillo", resultado.getNombre());
        verify(cartaRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Debería obtener carta por ID")
    void deberiaObtenerCartaPorId() {
        when(cartaRepository.findById(1L)).thenReturn(Optional.of(carta));
        when(cartaMapper.toDto(carta)).thenReturn(cartaDto);

        CartaDto resultado = cartaService.obtenerCartaPorId(1L);

        assertEquals("Cuchillo", resultado.getNombre());
    }

    @Test
    @DisplayName("Debería lanzar excepción si carta no existe")
    void deberiaFallarSiCartaNoExiste() {
        when(cartaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cartaService.obtenerCartaPorId(99L));
    }

    @Test
    @DisplayName("Debería obtener cartas por tipo")
    void deberiaObtenerCartasPorTipo() {
        when(cartaRepository.findByTipo(Carta.TipoCarta.ARMA)).thenReturn(List.of(carta));
        when(cartaMapper.toDtoList(any())).thenReturn(List.of(cartaDto));

        List<CartaDto> resultado = cartaService.obtenerCartasPorTipo("arma");

        assertEquals(1, resultado.size());
        verify(cartaRepository).findByTipo(Carta.TipoCarta.ARMA);
    }

    @Test
    @DisplayName("Debería lanzar excepción con tipo inválido")
    void deberiaFallarConTipoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> cartaService.obtenerCartasPorTipo("inexistente"));
    }

    @Test
    @DisplayName("Debería eliminar carta correctamente")
    void deberiaEliminarCarta() {
        when(cartaRepository.existsById(1L)).thenReturn(true);
        cartaService.eliminarCarta(1L);
        verify(cartaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debería fallar al eliminar carta inexistente")
    void deberiaFallarAlEliminarCartaInexistente() {
        when(cartaRepository.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> cartaService.eliminarCarta(99L));
    }
}
