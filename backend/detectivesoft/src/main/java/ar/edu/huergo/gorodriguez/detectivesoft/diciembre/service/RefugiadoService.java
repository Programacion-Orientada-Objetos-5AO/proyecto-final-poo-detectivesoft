package ar.edu.huergo.gorodriguez.detectivesoft.diciembre.service;

import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.NuevoRefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.RefugiadoDto;

public interface RefugiadoService {

    RefugiadoDto crearRefugiado(NuevoRefugiadoDto dto);

    RefugiadoDto obtenerRefugiadoPorId(Long id);

    List<RefugiadoDto> obtenerTodosLosRefugiados();

    List<RefugiadoDto> obtenerRefugiadosPorTipo(String tipo);

    RefugiadoDto actualizarRefugiado(Long id, RefugiadoDto dto);

    void eliminarRefugiado(Long id);

    RefugiadoDto actualizarEstadoRefugiado(Long id, Boolean adoptado);
}

