package ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaResumenDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Component
public class AnotadorMapper {

    public AnotadorDto toDto(Anotador anotador, List<CartaResumenDto> cartasDescartadas) {
        if (anotador == null) return null;

        return AnotadorDto.builder()
                .id(anotador.getId())
                .jugadorId(anotador.getJugador() != null ? anotador.getJugador().getId() : null)
                .jugadorNombre(anotador.getJugador() != null ? anotador.getJugador().getUsername() : null)
                .partidaId(anotador.getPartida() != null ? anotador.getPartida().getId() : null)
                .cartasDescartadas(cartasDescartadas)
                .build();
    }


    public Anotador toEntity(AnotadorDto dto, Jugador jugador, Partida partida) {
        if (dto == null) return null;
        Anotador anotador = new Anotador();
        anotador.setId(dto.getId());
        anotador.setJugador(jugador);
        anotador.setPartida(partida);
        return anotador;
    }

    public List<AnotadorDto> toDtoList(
            List<Anotador> anotadores,
            List<List<CartaResumenDto>> cartasPorAnotador
    ) {
        return java.util.stream.IntStream.range(0, anotadores.size())
                .mapToObj(i -> toDto(anotadores.get(i), cartasPorAnotador.get(i)))
                .toList();
    }

}
