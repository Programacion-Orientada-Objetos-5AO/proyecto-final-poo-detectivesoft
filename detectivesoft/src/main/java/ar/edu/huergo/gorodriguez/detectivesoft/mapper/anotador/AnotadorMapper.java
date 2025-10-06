package ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Component
public class AnotadorMapper {

    public AnotadorDto toDto(Anotador anotador) {
        if (anotador == null) return null;

        return AnotadorDto.builder()
                .id(anotador.getId())
                .jugadorId(anotador.getJugador() != null ? anotador.getJugador().getId() : null)
                .partidaId(anotador.getPartida() != null ? anotador.getPartida().getId() : null)
                .cartasDescartadas(anotador.getCartasDescartadas())
                .build();
    }

    public Anotador toEntity(AnotadorDto dto, Jugador jugador, Partida partida) {
        if (dto == null) return null;

        Anotador anotador = new Anotador();
        anotador.setId(dto.getId());
        anotador.setJugador(jugador);
        anotador.setPartida(partida);
        anotador.setCartasDescartadas(dto.getCartasDescartadas());

        return anotador;
    }

    public List<AnotadorDto> toDtoList(List<Anotador> anotadores) {
        return anotadores.stream().map(this::toDto).collect(Collectors.toList());
    }
}
