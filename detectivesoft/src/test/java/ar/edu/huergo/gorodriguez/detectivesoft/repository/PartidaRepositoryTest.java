package ar.edu.huergo.gorodriguez.detectivesoft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDateTime;
import java.util.List;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;

@DataJpaTest
@DisplayName("Tests de Integración - PartidaRepository")
class PartidaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PartidaRepository partidaRepository;

    private Partida partida1;
    private Partida partida2;

    @BeforeEach
    void setUp() {
        partida1 = new Partida();
        partida1.setCodigo("ABC123");
        partida1.setEstado(EstadoPartida.PENDIENTE);
        partida1.setFechaCreacion(LocalDateTime.now().minusDays(1));

        partida2 = new Partida();
        partida2.setCodigo("XYZ789");
        partida2.setEstado(EstadoPartida.EN_CURSO);
        partida2.setFechaCreacion(LocalDateTime.now());

        entityManager.persistAndFlush(partida1);
        entityManager.persistAndFlush(partida2);
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar partida por código")
    void deberiaEncontrarPartidaPorCodigo() {
        Optional<Partida> resultado = partidaRepository.findByCodigo("ABC123");
        assertTrue(resultado.isPresent());
        assertEquals(EstadoPartida.PENDIENTE, resultado.get().getEstado());
    }

    @Test
    @DisplayName("Debería verificar existencia por código")
    void deberiaVerificarExistenciaPorCodigo() {
        assertTrue(partidaRepository.existsByCodigo("XYZ789"));
        assertFalse(partidaRepository.existsByCodigo("NO_EXISTE"));
    }

    @Test
    @DisplayName("Debería encontrar partidas por estado")
    void deberiaEncontrarPartidasPorEstado() {
        List<Partida> pendientes = partidaRepository.findByEstado("PENDIENTE");
        List<Partida> enCurso = partidaRepository.findByEstado("EN_CURSO");

        assertEquals(1, pendientes.size());
        assertEquals("ABC123", pendientes.get(0).getCodigo());

        assertEquals(1, enCurso.size());
        assertEquals("XYZ789", enCurso.get(0).getCodigo());
    }

    @Test
    @DisplayName("Debería encontrar partidas recientes según fecha de creación")
    void deberiaEncontrarPartidasRecientes() {
        LocalDateTime ayer = LocalDateTime.now().minusHours(12);
        List<Partida> recientes = partidaRepository.findByFechaCreacionAfter(ayer);

        assertEquals(1, recientes.size());
        assertEquals("XYZ789", recientes.get(0).getCodigo());
    }

    @Test
    @DisplayName("Debería guardar y recuperar partida correctamente")
    void deberiaGuardarYRecuperarPartida() {
        Partida nueva = new Partida();
        nueva.setCodigo("NEW456");
        nueva.setEstado(EstadoPartida.PENDIENTE);
        nueva.setFechaCreacion(LocalDateTime.now());

        Partida guardada = partidaRepository.save(nueva);
        entityManager.flush();
        entityManager.clear();

        Optional<Partida> recuperada = partidaRepository.findById(guardada.getId());
        assertTrue(recuperada.isPresent());
        assertEquals("NEW456", recuperada.get().getCodigo());
    }
}
