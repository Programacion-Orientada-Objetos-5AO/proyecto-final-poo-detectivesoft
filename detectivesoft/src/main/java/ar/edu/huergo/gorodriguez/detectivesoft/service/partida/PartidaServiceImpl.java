package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida.PartidaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final PartidaMapper partidaMapper;

    @Override
    public PartidaDto crearPartida(Long creadorId) {
        Jugador creador = jugadorRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        Partida partida = new Partida();
        partida.setCodigo(generarCodigoUnico());
        partida.setEstado(EstadoPartida.PENDIENTE);
        partida.setFechaCreacion(LocalDateTime.now());
        partida.setMaxJugadores(6);
        partida.getJugadores().add(creador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    @Override
    public PartidaDto unirseAPartida(String codigo, Long jugadorId) {
        Partida partida = partidaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        if (partida.getJugadores().size() >= partida.getMaxJugadores()) {
            throw new RuntimeException("La partida ya está llena");
        }

        partida.getJugadores().add(jugador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    @Override
    public List<PartidaDto> listarPartidas() {
        return partidaRepository.findAll()
                .stream()
                .map(partidaMapper::toDto)
                .toList();
    }

    @Override
    public PartidaDto obtenerPartidaPorId(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        return partidaMapper.toDto(partida);
    }

    private String generarCodigoUnico() {
        return Long.toHexString(System.currentTimeMillis()).substring(6).toUpperCase();
    }

    @Override
    public void eliminarPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        partida.getJugadores().clear();
        partidaRepository.delete(partida);
    }

    @Override
    public PartidaDto eliminarJugadorDePartida(Long partidaId, Long jugadorId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        // Remueve jugador de la lista
        partida.getJugadores().remove(jugador);

        // Guardar cambios
        Partida actualizada = partidaRepository.save(partida);

        return partidaMapper.toDto(actualizada);
    }

}
