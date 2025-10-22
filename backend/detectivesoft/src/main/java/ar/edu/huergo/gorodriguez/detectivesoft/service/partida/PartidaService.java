package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import java.util.List;
import java.util.Map;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;

public interface PartidaService {

    PartidaDto crearPartida(Long creadorId);

    PartidaDto unirseAPartida(String codigo);

    PartidaDto iniciarPartida(Long partidaId);

    List<PartidaDto> listarPartidas();

    PartidaDto obtenerPartidaPorId(Long id);

    void eliminarPartida(Long id);

    PartidaDto eliminarJugadorDePartida(Long partidaId, Long jugadorId);

    Map<String, Object> obtenerEstadoPartida(Long id);
}
