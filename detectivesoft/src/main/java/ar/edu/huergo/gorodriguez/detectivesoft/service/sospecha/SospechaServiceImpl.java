package ar.edu.huergo.gorodriguez.detectivesoft.service.sospecha;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha.SospechaRequest;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha.SospechaResultadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.turno.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SospechaServiceImpl implements SospechaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final CartaRepository cartaRepository;
    private final TurnoRepository turnoRepository;

    @Override
    public SospechaResultadoDto resolverSospecha(SospechaRequest dto) {
        Partida partida = partidaRepository.findById(dto.getPartidaId())
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("No hay un jugador autenticado.");
        }
        String username = auth.getName();

        Jugador jugadorActual = jugadorRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
        
        Turno turnoActual = partida.getTurnoActual();
        if (turnoActual == null || !turnoActual.isActivo() || 
            !turnoActual.getJugador().getId().equals(jugadorActual.getId())) {
            throw new IllegalStateException("No es tu turno para realizar una sospecha.");
        }

        Carta personaje = cartaRepository.findById(dto.getPersonajeId())
                .orElseThrow(() -> new EntityNotFoundException("Carta de personaje no encontrada"));
        Carta arma = cartaRepository.findById(dto.getArmaId())
                .orElseThrow(() -> new EntityNotFoundException("Carta de arma no encontrada"));
        Carta habitacion = cartaRepository.findById(dto.getHabitacionId())
                .orElseThrow(() -> new EntityNotFoundException("Carta de habitación no encontrada"));

        List<Carta> cartasSospechadas = List.of(personaje, arma, habitacion);
        log.info("Jugador {} realiza una sospecha con las cartas: {}, {}, {}",
                jugadorActual.getUsername(),
                personaje.getNombre(),
                arma.getNombre(),
                habitacion.getNombre());
  
        List<Jugador> jugadores = partida.getJugadores();
        int indexActual = jugadores.indexOf(jugadorActual);

        for (int i = 1; i < jugadores.size(); i++) {
            Jugador siguiente = jugadores.get((indexActual + i) % jugadores.size());

            List<Carta> coincidencias = siguiente.getCartas().stream()
                    .filter(cartasSospechadas::contains)
                    .toList();

            if (!coincidencias.isEmpty()) {
                Carta cartaMostrada = coincidencias.get(0);
                log.info("El jugador {} mostró la carta {}", siguiente.getUsername(), cartaMostrada.getNombre());

                return new SospechaResultadoDto(
                        siguiente.getUsername(),
                        cartaMostrada.getNombre(),
                        "El jugador " + siguiente.getUsername() + " mostró una carta al jugador actual."
                );
            }
        }

        avanzarTurno(partida);
        partidaRepository.save(partida);

        return new SospechaResultadoDto(
                null,
                null,
                "Ningún jugador pudo refutar la sospecha. Se avanza el turno."
        );
    }

    private void avanzarTurno(Partida partida) {
        Turno turnoActual = partida.getTurnoActual();
        if (turnoActual == null || partida.getJugadores().isEmpty()) return;

        turnoActual.setActivo(false);
        turnoActual.setFechaFin(java.time.LocalDateTime.now());
        turnoRepository.save(turnoActual);

        List<Jugador> jugadores = partida.getJugadores();
        int indiceActual = jugadores.indexOf(turnoActual.getJugador());
        Jugador siguiente = jugadores.get((indiceActual + 1) % jugadores.size());

        Turno nuevoTurno = new Turno();
        nuevoTurno.setPartida(partida);
        nuevoTurno.setJugador(siguiente);
        nuevoTurno.setNumeroTurno(turnoActual.getNumeroTurno() + 1);
        nuevoTurno.setActivo(true);
        nuevoTurno.setFechaInicio(java.time.LocalDateTime.now());

        turnoRepository.save(nuevoTurno);
        partida.setTurnoActual(nuevoTurno);
    }
}
