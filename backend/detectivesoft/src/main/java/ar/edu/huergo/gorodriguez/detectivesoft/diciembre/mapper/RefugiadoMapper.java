package ar.edu.huergo.gorodriguez.detectivesoft.diciembre.mapper;

import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.NuevoRefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.RefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.entity.Refugiado;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RefugiadoMapper {

    public Refugiado toEntity(NuevoRefugiadoDto Dto) {
        Refugiado refugiado = new Refugiado();
        refugiado.setNombre(Dto.getNombre());
        refugiado.setTipo(Dto.getTipo());
        refugiado.setEdad(Dto.getEdad());
        refugiado.setAdoptado(false);
        return refugiado;
    }

    public RefugiadoDto toDto(Refugiado refugiado) {
        RefugiadoDto refugiadoDto = new RefugiadoDto();
        refugiadoDto.setId(refugiado.getId());
        refugiadoDto.setNombre(refugiado.getNombre());
        refugiadoDto.setTipo(refugiado.getTipo());
        refugiadoDto.setEdad(refugiado.getEdad());
        refugiadoDto.setAdoptado(refugiado.getAdoptado());
        return refugiadoDto;
    }

    public List<RefugiadoDto> toDtoList(List<Refugiado> refugiados) {
        if (refugiados == null) return List.of();
        return refugiados.stream().map(this::toDto).collect(Collectors.toList());
    }
}
