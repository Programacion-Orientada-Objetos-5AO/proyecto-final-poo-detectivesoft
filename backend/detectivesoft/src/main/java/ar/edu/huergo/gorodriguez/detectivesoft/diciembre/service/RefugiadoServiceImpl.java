package ar.edu.huergo.gorodriguez.detectivesoft.diciembre.service;

import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.NuevoRefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.dto.RefugiadoDto;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.entity.Refugiado;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.repository.RefugiadoRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.diciembre.mapper.RefugiadoMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefugiadoServiceImpl implements RefugiadoService {

    private final RefugiadoRepository refugiadoRepository;
    private final RefugiadoMapper refugiadoMapper;

    @Override
    public RefugiadoDto crearRefugiado(NuevoRefugiadoDto dto) {
        Refugiado refugiado = refugiadoMapper.toEntity(dto);
        return refugiadoMapper.toDto(refugiadoRepository.save(refugiado));
    }

    @Override
    public RefugiadoDto obtenerRefugiadoPorId(Long id) {
        Refugiado refugiado = refugiadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el refugiado con este ID"));
        return refugiadoMapper.toDto(refugiado);
    }

    @Override
    public List<RefugiadoDto> obtenerTodosLosRefugiados() {
        return refugiadoMapper.toDtoList(refugiadoRepository.findAll());
    }

    @Override
    public List<RefugiadoDto> obtenerRefugiadosPorTipo(String tipo) {
        return refugiadoRepository.findByTipo(tipo).stream().map(refugiadoMapper::toDto).toList();
    }

    @Override
    public RefugiadoDto actualizarRefugiado(Long id, RefugiadoDto dto) {
        Refugiado refugiado = refugiadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el refugiado con este ID"));

        refugiado.setNombre(dto.getNombre());
        refugiado.setTipo(dto.getTipo());
        refugiado.setEdad(dto.getEdad());


        return refugiadoMapper.toDto(refugiadoRepository.save(refugiado));
    }

    @Override
    public void eliminarRefugiado(Long id) {
        if (!refugiadoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontro el refugiado con este ID");
        }
        refugiadoRepository.deleteById(id);
    }

    @Override
    public RefugiadoDto actualizarEstadoRefugiado(Long id, Boolean adoptado) {
        Refugiado refugiado = refugiadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el refugiado con este ID"));

        refugiado.setAdoptado(adoptado);

        return refugiadoMapper.toDto(refugiadoRepository.save(refugiado));
    }
}
