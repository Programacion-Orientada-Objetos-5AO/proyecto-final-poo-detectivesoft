package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.mapper.partida.PartidaMapper;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        Partida partida = new Partida();
        partida.setCodigo(generarCodigoUnico());
        partida.setEstado(EstadoPartida.PENDIENTE);
        partida.setFechaCreacion(LocalDateTime.now());
        partida.setMaxJugadores(6);
        partida.agregarJugador(creador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    @Override
    public PartidaDto unirseAPartida(String codigo, Long jugadorId) {
        Partida partida = partidaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        if (partida.getJugadores().contains(jugador)) {
            throw new IllegalStateException("El jugador ya está en la partida");
        }
        partida.agregarJugador(jugador);

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
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        return partidaMapper.toDto(partida);
    }

    @Override
    public void eliminarPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        partida.getJugadores().clear();
        partidaRepository.delete(partida);
    }

    @Override
    public PartidaDto eliminarJugadorDePartida(Long partidaId, Long jugadorId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));

        partida.removerJugador(jugador);

        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    public List<PartidaDto> listarPartidasPorEstado(EstadoPartida estado) {
        return partidaRepository.findByEstado(estado.name())
                .stream()
                .map(partidaMapper::toDto)
                .toList();
    }

    public PartidaDto actualizarEstadoPartida(Long partidaId, EstadoPartida nuevoEstado) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        partida.setEstado(nuevoEstado);
        return partidaMapper.toDto(partidaRepository.save(partida));
    }

    private String generarCodigoUnico() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}