package ar.edu.huergo.gorodriguez.detectivesoft.service.sospecha;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha.SospechaRequest;
import ar.edu.huergo.gorodriguez.detectivesoft.dto.sospecha.SospechaResultadoDto;

public interface SospechaService {
    SospechaResultadoDto resolverSospecha(SospechaRequest dto);
}
