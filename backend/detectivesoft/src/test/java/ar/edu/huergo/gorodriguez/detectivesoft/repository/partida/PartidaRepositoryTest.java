package ar.edu.huergo.gorodriguez.detectivesoft.repository.partida;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@DataJpaTest
@DisplayName("Tests de Integración - PartidaRepository")
class PartidaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PartidaRepository partidaRepository;

    private Partida partida;

    @BeforeEach
    void setUp() {
        partida = new Partida();
        partida.setCodigo("XYZ123");
        partida.setEstado(Partida.EstadoPartida.PENDIENTE);
        partida.setFechaCreacion(LocalDateTime.now());
        partida.setMaxJugadores(6);
        entityManager.persistAndFlush(partida);
    }

    @Test
    @DisplayName("Debería encontrar partida por código")
    void deberiaEncontrarPorCodigo() {
        Optional<Partida> encontrada = partidaRepository.findByCodigo("XYZ123");
        assertTrue(encontrada.isPresent());
        assertEquals("XYZ123", encontrada.get().getCodigo());
    }

    @Test
    @DisplayName("Debería verificar existencia por código")
    void deberiaVerificarExistenciaPorCodigo() {
        assertTrue(partidaRepository.existsByCodigo("XYZ123"));
        assertFalse(partidaRepository.existsByCodigo("INEXISTENTE"));
    }

    @Test
    @DisplayName("Debería buscar partidas por estado")
    void deberiaBuscarPorEstado() {
        var partidas = partidaRepository.findByEstado("PENDIENTE");
        assertFalse(partidas.isEmpty());
        assertEquals(Partida.EstadoPartida.PENDIENTE, partidas.get(0).getEstado());
    }

    @Test
    @DisplayName("Debería buscar partidas recientes")
    void deberiaBuscarPartidasRecientes() {
        var recientes = partidaRepository.findByFechaCreacionAfter(LocalDateTime.now().minusMinutes(1));
        assertFalse(recientes.isEmpty());
    }
}
