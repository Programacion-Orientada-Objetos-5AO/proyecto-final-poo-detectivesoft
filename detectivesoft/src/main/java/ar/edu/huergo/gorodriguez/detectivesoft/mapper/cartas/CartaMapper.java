package ar.edu.huergo.gorodriguez.detectivesoft.mapper.cartas;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.cartas.CartaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.cartas.CartaResumenDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.cartas.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.cartas.Carta.TipoCarta;

@Component
public class CartaMapper {

    public CartaDto toDto(Carta carta) {
        if (carta == null) {
            return null;
        }
        return CartaDto.builder()
                .id(carta.getId())
                .nombre(carta.getNombre())
                .tipo(carta.getTipo().name())
                .partidaId(carta.getPartida() != null ? carta.getPartida().getId() : null)
                .jugadorId(carta.getJugador() != null ? carta.getJugador().getId() : null)
                .build();
    }

    public CartaResumenDto toResumenDto(Carta carta) {
        if (carta == null) {
            return null;
        }
        return CartaResumenDto.builder()
                .id(carta.getId())
                .nombre(carta.getNombre())
                .build();
    }

    public List<CartaDto> toDtoList(List<Carta> cartas) {
        return cartas.stream()
                .map(this::toDto)
                .toList();
    }

    public Carta toEntity(CartaDto cartaDTO) {
        if (cartaDTO == null) {
            return null;
        }
        Carta carta = new Carta();
        carta.setNombre(cartaDTO.getNombre());
        carta.setTipo(TipoCarta.valueOf(cartaDTO.getTipo()));
        return carta;
    }
}
