package ar.edu.huergo.practicaParcial;

import ar.edu.huergo.practicaParcial.NuevoRefugiadoDto;
import ar.edu.huergo.practicaParcial.RefugiadoDto;
import ar.edu.huergo.practicaParcial.Refugiado;

import org.springframework.stereotype.Component;

@Component
public class RefugiadoMapper {
    public Refugiado toEntity(NuevoRefugiadoDto dto) {
        Refugiado refugiado = new Refugiado();
        refugiado.setNombre(dto.getNombre());
        refugiado.setTipo(dto.getTipo());
        refugiado.setEdad(dto.getEdad());
        refugiado.setAdoptado(false);
        return refugiado;
    }

    public RefugiadoDto toDTO(Refugiado refugiado) {
        RefugiadoDto refugiadoDto = new RefugiadoDto();
        refugiadoDto.setId(refugiado.getId());
        refugiadoDto.setNombre(refugiado.getNombre());
        refugiadoDto.setTipo(refugiado.getTipo());
        refugiadoDto.setEdad(refugiado.getEdad());
        refugiadoDto.setAdoptado(refugiado.getAdoptado());
        return refugiadoDto;
    }
}
