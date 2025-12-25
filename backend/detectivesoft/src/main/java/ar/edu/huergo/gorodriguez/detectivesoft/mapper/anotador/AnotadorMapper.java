package ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaResumenDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;

@Component
public class AnotadorMapper {

    public AnotadorDto toDto(Anotador anotador) {
        if (anotador == null) return null;

        List<CartaResumenDto> cartasDto = anotador.getCartasDescartadas().stream()
                .map(c -> new CartaResumenDto(c.getId(), c.getNombre()))
                .collect(Collectors.toList());

        return AnotadorDto.builder()
                .id(anotador.getId())
                .jugadorId(anotador.getJugador().getId())
                .jugadorNombre(anotador.getJugador().getUsername())
                .partidaId(anotador.getPartida().getId())
                .cartasDescartadas(cartasDto)
                .build();
    }

    public List<AnotadorDto> toDtoList(List<Anotador> anotadores) {
        return anotadores.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
