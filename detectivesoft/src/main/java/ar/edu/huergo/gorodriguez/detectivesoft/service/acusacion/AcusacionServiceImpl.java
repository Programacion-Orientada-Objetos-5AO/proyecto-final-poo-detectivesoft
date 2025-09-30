package ar.edu.huergo.gorodriguez.detectivesoft.service.acusacion;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.acusacion.AcusacionMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion.AcusacionRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcusacionServiceImpl implements AcusacionService {

    private final AcusacionRepository acusacionRepository;
    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final AcusacionMapper acusacionMapper;

    @Override
    public AcusacionDto crearAcusacion(AcusacionDto dto) {
        Partida partida = partidaRepository.findById(dto.getPartidaId())
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        Acusacion acusacion = acusacionMapper.toEntity(dto);
        acusacion.setPartida(partida);
        acusacion.setJugador(jugador);

        return acusacionMapper.toDto(acusacionRepository.save(acusacion));
    }

    @Override
    public List<AcusacionDto> obtenerTodas() {
        return acusacionMapper.toDtoList(acusacionRepository.findAll());
    }

    @Override
    public List<AcusacionDto> obtenerPorPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return acusacionMapper.toDtoList(acusacionRepository.findByPartida(partida));
    }

    @Override
    public List<AcusacionDto> obtenerPorJugador(Long jugadorId) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
        return acusacionMapper.toDtoList(acusacionRepository.findByJugador(jugador));
    }

    @Override
    public boolean existeAcusacionCorrecta(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return !acusacionRepository.findByPartidaAndCorrectaTrue(partida).isEmpty();
    }

    @Override
    public void eliminarAcusacion(Long id) {
        if (!acusacionRepository.existsById(id)) {
            throw new EntityNotFoundException("Acusacion no encontrada");
        }
        acusacionRepository.deleteById(id);
    }
}
