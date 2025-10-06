package ar.edu.huergo.gorodriguez.detectivesoft.service.anotador;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.anotador.AnotadorMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador.AnotadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
    public AnotadorDto actualizarCartasDescartadas(Long anotadorId, List<Long> nuevasCartasDescartadas) {
        Anotador anotador = anotadorRepository.findById(anotadorId)
                .orElseThrow(() -> new EntityNotFoundException("Anotador no encontrado"));
        anotador.setCartasDescartadas(nuevasCartasDescartadas);
        return anotadorMapper.toDto(anotadorRepository.save(anotador));
    }

    @Override
    public void eliminarAnotador(Long id) {
        if (!anotadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Anotador no encontrado");
        }
        anotadorRepository.deleteById(id);
    }
}
