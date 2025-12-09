package ar.edu.huergo.practicaParcial;

import ar.edu.huergo.practicaParcial.NuevoRefugiadoDto;
import ar.edu.huergo.practicaParcial.RefugiadoDto;
import ar.edu.huergo.practicaParcial.RefugiadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refugiados")
public class RefugiadoController {

    @Autowired
    private RefugiadoService service;

    @PostMapping
    public RefugiadoDto crear(@RequestBody NuevoRefugiadoDto Dto) {
        return service.crear(Dto);
    }

    @GetMapping
    public List<RefugiadoDto> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public RefugiadoDto obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public RefugiadoDto actualizar(@PathVariable Long id, @RequestBody NuevoRefugiadoDto Dto) {
        return service.actualizar(id, Dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PatchMapping("/{id}/Adoptado")
    public RefugiadoDto cambiarAdoptado(@PathVariable Long id, @RequestParam Boolean Adoptado) {
        return service.cambiarAdoptado(id, Adoptado);
    }

    @GetMapping("/filtrar")
    public List<RefugiadoDto> filtrar(@RequestParam String valor) {
        return service.filtrarPorTipo(valor);
    }
}
