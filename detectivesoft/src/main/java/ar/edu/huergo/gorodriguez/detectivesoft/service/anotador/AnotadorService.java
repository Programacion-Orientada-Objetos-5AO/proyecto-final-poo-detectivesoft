package ar.edu.huergo.gorodriguez.detectivesoft.service.anotador;

import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.anotador.AnotadorDto;

public interface AnotadorService {

    AnotadorDto crearAnotador(Long jugadorId, Long partidaId);

    AnotadorDto obtenerPorId(Long id);

    List<AnotadorDto> listarPorPartida(Long partidaId);

    AnotadorDto actualizarCartasDescartadas(Long anotadorId, List<Long> nuevasCartasDescartadas);

    void eliminarAnotador(Long id);
}
