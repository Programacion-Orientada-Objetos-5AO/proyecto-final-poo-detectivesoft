package ar.edu.huergo.practicaParcial;

import ar.edu.huergo.practicaParcial.NuevoRefugiadoDto;
import ar.edu.huergo.practicaParcial.RefugiadoDto;
import ar.edu.huergo.practicaParcial.Refugiado;
import ar.edu.huergo.practicaParcial.RefugiadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RefugiadoService {

    @Autowired
    private RefugiadoRepository refugiadoRepository;

    @Autowired
    private RefugiadoMapper mapper;


    public RefugiadoDto crear(NuevoRefugiadoDto Dto) {
        Refugiado refugiado = mapper.toEntity(Dto);
        return mapper.toDTO(refugiadoRepository.save(refugiado));
    }

    public List<RefugiadoDto> obtenerTodos() {
        return refugiadoRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    public RefugiadoDto obtenerPorId(Long id) {
        return refugiadoRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    public RefugiadoDto actualizar(Long id, NuevoRefugiadoDto Dto) {
        Refugiado refugiado = refugiadoRepository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));

        refugiado.setNombre(Dto.getNombre());
        refugiado.setTipo(Dto.getTipo());
        refugiado.setEdad(Dto.getEdad());

        return mapper.toDTO(refugiadoRepository.save(refugiado));
    }

    public void eliminar(Long id) {
        refugiadoRepository.deleteById(id);
    }

    public RefugiadoDto cambiarAdoptado(Long id, Boolean adoptado) {
        Refugiado refugiado = refugiadoRepository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        refugiado.setAdoptado(adoptado);
        return mapper.toDTO(refugiadoRepository.save(refugiado));
    }

    public List<RefugiadoDto> filtrarPorTipo(String valor) {
        return refugiadoRepository.findByTipo(valor).stream().map(mapper::toDTO).toList();
    }
    
}
