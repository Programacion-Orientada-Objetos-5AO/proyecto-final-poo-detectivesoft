package ar.edu.huergo.gorodriguez.detectivesoft.mapper.acusacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@Component
public class AcusacionMapper {

    public AcusacionDto toDto(Acusacion acusacion) {
        if (acusacion == null) return null;

        return AcusacionDto.builder()
                .id(acusacion.getId())
                .partidaId(acusacion.getPartida() != null ? acusacion.getPartida().getId() : null)
                .jugadorId(acusacion.getJugador() != null ? acusacion.getJugador().getId() : null)
                .personajeId(acusacion.getPersonaje() != null ? acusacion.getPersonaje().getId() : null)
                .armaId(acusacion.getArma() != null ? acusacion.getArma().getId() : null)
                .habitacionId(acusacion.getHabitacion() != null ? acusacion.getHabitacion().getId() : null)
                .fecha(acusacion.getFecha())
                .correcta(acusacion.getCorrecta())
                .build();
    }

    public Acusacion toEntity(AcusacionDto dto) {
        if (dto == null) return null;

        Acusacion acusacion = new Acusacion();
        acusacion.setId(dto.getId());

        if (dto.getPartidaId() != null) {
            Partida partida = new Partida();
            partida.setId(dto.getPartidaId());
            acusacion.setPartida(partida);
        }

        if (dto.getJugadorId() != null) {
            Jugador jugador = new Jugador();
            jugador.setId(dto.getJugadorId());
            acusacion.setJugador(jugador);
        }

        if (dto.getPersonajeId() != null) {
            Carta personaje = new Carta();
            personaje.setId(dto.getPersonajeId());
            acusacion.setPersonaje(personaje);
        }

        if (dto.getArmaId() != null) {
            Carta arma = new Carta();
            arma.setId(dto.getArmaId());
            acusacion.setArma(arma);
        }

        if (dto.getHabitacionId() != null) {
            Carta habitacion = new Carta();
            habitacion.setId(dto.getHabitacionId());
            acusacion.setHabitacion(habitacion);
        }

        acusacion.setFecha(dto.getFecha());
        acusacion.setCorrecta(dto.getCorrecta());

        return acusacion;
    }

    public List<AcusacionDto> toDtoList(List<Acusacion> acusaciones) {
        return acusaciones.stream().map(this::toDto).collect(Collectors.toList());
    }
}
