package ar.edu.huergo.gorodriguez.detectivesoft.service.carta;

import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaDto;

public interface CartaService {

    CartaDto crearCarta(CartaDto dto);

    CartaDto obtenerCartaPorId(Long id);

    List<CartaDto> obtenerTodasLasCartas();

    List<CartaDto> obtenerCartasPorPartida(Long partidaId);

    List<CartaDto> obtenerCartasPorJugador(Long jugadorId);

    List<CartaDto> obtenerCartasSinRepartir(Long partidaId);

    CartaDto actualizarCarta(Long id, CartaDto dto);

    void eliminarCarta(Long id);
}

