package ar.edu.huergo.gorodriguez.detectivesoft.service.acusacion;

import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.acusacion.AcusacionDto;

public interface AcusacionService {

    AcusacionDto crearAcusacion(AcusacionDto acusacionDto);

    List<AcusacionDto> obtenerTodas();

    List<AcusacionDto> obtenerPorPartida(Long partidaId);

    List<AcusacionDto> obtenerPorJugador(Long jugadorId);

    boolean existeAcusacionCorrecta(Long partidaId);

    void eliminarAcusacion(Long id);
}
