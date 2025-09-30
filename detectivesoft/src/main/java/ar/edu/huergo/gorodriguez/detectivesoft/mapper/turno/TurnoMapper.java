package ar.edu.huergo.gorodriguez.detectivesoft.mapper.turno;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.turno.TurnoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;

@Component
public class TurnoMapper {

    public TurnoDto toDto(Turno turno) {
        if (turno == null) {
            return null;
        }

        return TurnoDto.builder()
                .id(turno.getId())
                .partidaId(turno.getPartida() != null ? turno.getPartida().getId() : null)
                .jugadorId(turno.getJugador() != null ? turno.getJugador().getId() : null)
                .numeroTurno(turno.getNumeroTurno())
                .activo(turno.isActivo())
                .fechaInicio(turno.getFechaInicio())
                .fechaFin(turno.getFechaFin())
                .build();
    }

    public Turno toEntity(TurnoDto dto) {
        if (dto == null) {
            return null;
        }

        Turno turno = new Turno();
        turno.setId(dto.getId());
        turno.setNumeroTurno(dto.getNumeroTurno());
        turno.setActivo(dto.isActivo());
        turno.setFechaInicio(dto.getFechaInicio());
        turno.setFechaFin(dto.getFechaFin());

        return turno;
    }
}
