package ar.edu.huergo.gorodriguez.detectivesoft.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.RegistrarDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.jugador.JugadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.Jugador;

@Component
public class JugadorMapper {

    public JugadorDto toDto(Jugador jugador) {
        if (jugador == null) {
            return null;
        }
        return JugadorDto.builder()
                .id(jugador.getId())
                .nombre(jugador.getUsername())
                .email(jugador.getEmail())
                .partidasJugadas(jugador.getPartidasJugadas())
                .partidasGanadas(jugador.getPartidasGanadas())
                .build();
    }

    public List<JugadorDto> toDtoList(List<Jugador> jugadores) {
        return jugadores.stream()
                .map(this::toDto)
                .toList();
    }

    public Jugador toEntity(RegistrarDto registrarDTO) {
        if (registrarDTO == null) {
            return null;
        }
        Jugador jugador = new Jugador();
        jugador.setEmail(registrarDTO.email());
        jugador.setUsername(registrarDTO.username());

        return jugador;
    }
}
