package ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaResumenDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Component
public class AnotadorMapper {

    public AnotadorDto toDto(Anotador anotador, List<Carta> cartasDescartadas) {
        if (anotador == null) return null;

        List<CartaResumenDto> cartasDto = cartasDescartadas.stream()
                .map(c -> new CartaResumenDto(c.getId(), c.getNombre(), c.getTipo().name()))
                .collect(Collectors.toList());

        return AnotadorDto.builder()
                .id(anotador.getId())
                .jugadorId(anotador.getJugador() != null ? anotador.getJugador().getId() : null)
                .jugadorNombre(anotador.getJugador() != null ? anotador.getJugador().getUsername() : null)
                .partidaId(anotador.getPartida() != null ? anotador.getPartida().getId() : null)
                .cartasDescartadas(cartasDto)
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

    public List<AnotadorDto> toDtoList(List<Anotador> anotadores, List<List<Carta>> cartasPorAnotador) {
        return anotadores.stream()
                .map(a -> toDto(a, cartasPorAnotador.get(anotadores.indexOf(a))))
                .collect(Collectors.toList());
    }
}
