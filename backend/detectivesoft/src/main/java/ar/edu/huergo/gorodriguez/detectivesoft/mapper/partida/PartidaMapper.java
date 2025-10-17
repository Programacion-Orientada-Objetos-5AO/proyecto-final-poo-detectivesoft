package ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida;

import org.springframework.stereotype.Component;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.jugador.JugadorResumenDto;

@Component
public class PartidaMapper {

    public PartidaDto toDto(Partida partida) {
        PartidaDto dto = new PartidaDto();
        dto.setId(partida.getId());
        dto.setCodigo(partida.getCodigo());
        dto.setEstado(partida.getEstado().name());
        dto.setFechaCreacion(partida.getFechaCreacion());
        dto.setMaxJugadores(partida.getMaxJugadores());
        dto.setRecuentoJugadores(partida.getJugadores().size());

        dto.setJugadores(
            partida.getJugadores()
                   .stream()
                   .map(j -> {
                       JugadorResumenDto resumen = new JugadorResumenDto();
                       resumen.setId(j.getId());
                       resumen.setNombre(j.getUsername());
                       return resumen;
                   })
                   .toList()
        );

        return dto;
    }
}