package ar.edu.huergo.gorodriguez.detectivesoft.service.partida;

import java.util.List;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.partida.PartidaDto;

public interface PartidaService {

    // Crear una nueva partida
    PartidaDto crearPartida(Long creadorId);

    // Un jugador se une a una partida
    PartidaDto unirseAPartida(String codigo, Long jugadorId);

    // Iniciar una partida
    PartidaDto iniciarPartida(Long partidaId);

    // Listar todas las partidas
    List<PartidaDto> listarPartidas();

    // Obtener partida por ID
    PartidaDto obtenerPartidaPorId(Long id);

    // Eliminar partida por ID
    void eliminarPartida(Long id);

    // Eliminar Jugador de una partida
    PartidaDto eliminarJugadorDePartida(Long partidaId, Long jugadorId);
}
