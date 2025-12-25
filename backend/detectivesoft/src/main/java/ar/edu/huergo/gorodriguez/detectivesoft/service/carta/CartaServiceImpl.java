package ar.edu.huergo.gorodriguez.detectivesoft.service.carta;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.carta.CartaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaServiceImpl implements CartaService {

    private final CartaRepository cartaRepository;
    private final CartaMapper cartaMapper;

    @Override
    public CartaDto crearCarta(CartaDto dto) {
        Carta carta = cartaMapper.toEntity(dto);
        carta.setImagen(dto.getImagen());
        return cartaMapper.toDto(cartaRepository.save(carta));
    }

    @Override
    public CartaDto obtenerCartaPorId(Long id) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carta con id " + id + " no encontrada"));
        return cartaMapper.toDto(carta);
    }

    @Override
    public List<CartaDto> obtenerTodasLasCartas() {
        return cartaMapper.toDtoList(cartaRepository.findAll());
    }

    @Override
    public List<CartaDto> obtenerCartasPorTipo(String tipo) {
        try {
            Carta.TipoCarta tipoEnum = Carta.TipoCarta.valueOf(tipo.toUpperCase());
            return cartaMapper.toDtoList(cartaRepository.findByTipo(tipoEnum));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de carta inválido: " + tipo);
        }
    }


    @Override
    public List<CartaDto> obtenerCartasPorPartida(Long partidaId) {
        return cartaMapper.toDtoList(cartaRepository.findByPartidaId(partidaId));
    }

    @Override
    public List<CartaDto> obtenerCartasPorJugador(Long jugadorId) {
        return cartaMapper.toDtoList(cartaRepository.findByJugadorId(jugadorId));
    }

    @Override
    public List<CartaDto> obtenerCartasSinRepartir(Long partidaId) {
        return cartaMapper.toDtoList(cartaRepository.findByPartidaIdAndJugadorIsNull(partidaId));
    }

    @Override
    public CartaDto actualizarCarta(Long id, CartaDto dto) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carta con id " + id + " no encontrada"));

        carta.setNombre(dto.getNombre());
        carta.setImagen(dto.getImagen());

        try {
            carta.setTipo(Carta.TipoCarta.valueOf(dto.getTipo()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de carta inválido: " + dto.getTipo());
        }
        return cartaMapper.toDto(cartaRepository.save(carta));
    }

    @Override
    public void eliminarCarta(Long id) {
        if (!cartaRepository.existsById(id)) {
            throw new EntityNotFoundException("Carta con id " + id + " no encontrada");
        }
        cartaRepository.deleteById(id);
    }
}
