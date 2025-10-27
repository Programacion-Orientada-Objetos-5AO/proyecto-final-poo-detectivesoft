package ar.edu.huergo.gorodriguez.detectivesoft.service.anotador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador.AnotadorMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador.AnotadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
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
    private final CartaRepository cartaRepository;
    private final AnotadorMapper anotadorMapper;

    @Override
    @Transactional
    public AnotadorDto crearAnotador(Long jugadorId, Long partidaId) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Anotador anotador = new Anotador();
        anotador.setJugador(jugador);
        anotador.setPartida(partida);
        anotador.setCartasDescartadas(List.of());

        return anotadorMapper.toDto(anotadorRepository.save(anotador));
    }

    @Override
    public AnotadorDto obtenerPorId(Long id) {
        Anotador anotador = anotadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado"));
        return anotadorMapper.toDto(anotador);
    }

    @Override
    public List<AnotadorDto> listarPorPartida(Long partidaId) {
        return anotadorMapper.toDtoList(anotadorRepository.findByPartidaId(partidaId));
    }

    @Override
    @Transactional
    public AnotadorDto actualizarCartasDescartadas(Long anotadorId, List<Long> nuevasCartasDescartadas) {
        Anotador anotador = anotadorRepository.findById(anotadorId)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Jugador jugador = jugadorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Jugador autenticado no encontrado"));

        if (!anotador.getJugador().getId().equals(jugador.getId())) {
            throw new IllegalStateException("No puedes editar el anotador de otro jugador.");
        }

        List<Carta> nuevasCartas = nuevasCartasDescartadas.stream()
                .map(id -> cartaRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Carta no encontrada: " + id)))
                .collect(Collectors.toList());

        anotador.setCartasDescartadas(nuevasCartas);
        anotadorRepository.save(anotador);

        return anotadorMapper.toDto(anotador);
    }

    @Override
    public void eliminarAnotador(Long id) {
        if (!anotadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Anotador no encontrado");
        }
        anotadorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void crearAnotadoresParaPartida(Partida partida) {
        for (Jugador jugador : partida.getJugadores()) {
            Anotador anotador = new Anotador();
            anotador.setJugador(jugador);
            anotador.setPartida(partida);
            anotador.setCartasDescartadas(new java.util.ArrayList<>(jugador.getCartas()));
            anotadorRepository.save(anotador);
        }
    }

    @Override
    @Transactional
    public void marcarCartaComoDescartada(Long jugadorId, Long cartaId) {
        Anotador anotador = anotadorRepository.findByJugadorId(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado para el jugador"));

        Carta carta = cartaRepository.findById(cartaId)
                .orElseThrow(() -> new EntityNotFoundException("Carta no encontrada"));

        if (!anotador.getCartasDescartadas().contains(carta)) {
            anotador.getCartasDescartadas().add(carta);
            anotadorRepository.save(anotador);
            log.info("Carta {} marcada como descartada en el anotador del jugador {}", carta.getNombre(), jugadorId);
        }
    }
}
