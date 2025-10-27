package ar.edu.huergo.gorodriguez.detectivesoft.service.anotador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.carta.CartaResumenDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador.AnotadorMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador.AnotadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor

@Slf4j
public class AnotadorServiceImpl implements AnotadorService {

    private final AnotadorRepository anotadorRepository;
    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final AnotadorMapper anotadorMapper;

    @Override
    public AnotadorDto crearAnotador(Long jugadorId, Long partidaId) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Anotador anotador = new Anotador();
        anotador.setJugador(jugador);
        anotador.setPartida(partida);

        anotador = anotadorRepository.save(anotador);

        List<CartaResumenDto> cartasJugador = jugador.getCartas().stream()
                .map(c -> new CartaResumenDto(c.getId(), c.getNombre(), c.getTipo().name()))
                .toList();

        return anotadorMapper.toDto(anotador, cartasJugador);
    }

    @Override
    public AnotadorDto obtenerPorId(Long id) {
        Anotador anotador = anotadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado"));

        List<CartaResumenDto> cartas = anotador.getJugador().getCartas().stream()
                .map(c -> new CartaResumenDto(c.getId(), c.getNombre(), c.getTipo().name()))
                .toList();

        return anotadorMapper.toDto(anotador, cartas);
    }

    @Override
    public List<AnotadorDto> listarPorPartida(Long partidaId) {
        List<Anotador> anotadores = anotadorRepository.findByPartidaId(partidaId);

        List<List<CartaResumenDto>> cartasPorAnotador = anotadores.stream()
                .map(a -> a.getJugador().getCartas().stream()
                        .map(c -> new CartaResumenDto(c.getId(), c.getNombre(), c.getTipo().name()))
                        .toList())
                .toList();

        return anotadorMapper.toDtoList(anotadores, cartasPorAnotador);
    }

    @Override
    public AnotadorDto actualizarCartasDescartadas(Long anotadorId, List<Long> nuevasCartasDescartadas) {
        Anotador anotador = anotadorRepository.findById(anotadorId)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Jugador jugador = jugadorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        if (!anotador.getJugador().getId().equals(jugador.getId())) {
            throw new IllegalStateException("No puedes editar un anotador ajeno.");
        }

        anotador.setCartasDescartadas(nuevasCartasDescartadas);
        anotadorRepository.save(anotador);

        List<CartaResumenDto> cartasActuales = jugador.getCartas().stream()
                .map(c -> new CartaResumenDto(c.getId(), c.getNombre(), c.getTipo().name()))
                .toList();

        return anotadorMapper.toDto(anotador, cartasActuales);
    }


    @Override
    public void eliminarAnotador(Long id) {
        if (!anotadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Anotador no encontrado");
        }
        anotadorRepository.deleteById(id);
    }

    public void crearAnotadoresParaPartida(Partida partida) {
        for (Jugador jugador : partida.getJugadores()) {
            Anotador anotador = new Anotador();
            anotador.setJugador(jugador);
            anotador.setPartida(partida);

            List<Long> cartasJugador = jugador.getCartas()
                    .stream()
                    .map(Carta::getId)
                    .collect(Collectors.toList());
            anotador.setCartasDescartadas(cartasJugador);

            anotadorRepository.save(anotador);
        }
    }

    @Transactional
    public void marcarCartaComoDescartada(Long jugadorId, Long cartaId) {
        Anotador anotador = anotadorRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado para el jugador"));

        if (!anotador.getCartasDescartadas().contains(cartaId)) {
            anotador.getCartasDescartadas().add(cartaId);
            anotadorRepository.save(anotador);
            log.info("Carta {} marcada como descartada en el anotador del jugador {}", cartaId, jugadorId);
        }
    }

}
