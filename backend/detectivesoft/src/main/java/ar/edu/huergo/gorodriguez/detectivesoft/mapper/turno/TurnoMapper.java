package ar.edu.huergo.gorodriguez.detectivesoft.mapper.turno;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;

@Component
public class TurnoMapper {

    public TurnoDto toDto(Turno turno) {
        if (turno == null) return null;

        return TurnoDto.builder()
                .id(turno.getId())
                .numeroTurno(turno.getNumeroTurno())
                .activo(turno.isActivo())
                .fechaInicio(turno.getFechaInicio())
                .fechaFin(turno.getFechaFin())
                .partidaId(turno.getPartida() != null ? turno.getPartida().getId() : null)
                .jugadorId(turno.getJugador() != null ? turno.getJugador().getId() : null)
                .build();
    }

    public List<TurnoDto> toDtoList(List<Turno> turnos) {
        return turnos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
