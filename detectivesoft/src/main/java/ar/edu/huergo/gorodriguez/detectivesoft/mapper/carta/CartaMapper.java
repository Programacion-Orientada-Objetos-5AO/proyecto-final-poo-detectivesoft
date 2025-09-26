package ar.edu.huergo.gorodriguez.detectivesoft.mapper.carta;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;

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

    public Carta toEntity(CartaDto dto) {
        if (dto == null) {
            return null;
        }

        Carta carta = new Carta();
        carta.setId(dto.getId());
        carta.setNombre(dto.getNombre());

        if (dto.getTipo() != null) {
            carta.setTipo(Carta.TipoCarta.valueOf(dto.getTipo()));
        }

        return carta;
    }

    public List<CartaDto> toDtoList(List<Carta> cartas) {
        if (cartas == null) return List.of();
        return cartas.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Carta> toEntityList(List<CartaDto> dtos) {
        if (dtos == null) return List.of();
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
