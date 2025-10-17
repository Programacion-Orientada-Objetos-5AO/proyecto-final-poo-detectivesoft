package ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador.AnotadorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Tests de Integración - AnotadorRepository")
class AnotadorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnotadorRepository anotadorRepository;

    private Jugador jugador1;
    private Jugador jugador2;
    private Partida partida1;
    private Partida partida2;
    private Anotador anotador1;
    private Anotador anotador2;

    @BeforeEach
    void setUp() {
        // Crear jugadores
        jugador1 = new Jugador("jugador1@test.com", "jugador1", "pass1");
        jugador2 = new Jugador("jugador2@test.com", "jugador2", "pass2");

        // Crear partidas
        partida1 = new Partida();
        partida1.setCodigo("PARTIDA1");
        partida1.setEstado(EstadoPartida.PENDIENTE);

        partida2 = new Partida();
        partida2.setCodigo("PARTIDA2");
        partida2.setEstado(EstadoPartida.EN_CURSO);

        // Persistir dependencias
        entityManager.persistAndFlush(jugador1);
        entityManager.persistAndFlush(jugador2);
        entityManager.persistAndFlush(partida1);
        entityManager.persistAndFlush(partida2);

        // Crear anotadores
        anotador1 = new Anotador();
        anotador1.setJugador(jugador1);
        anotador1.setPartida(partida1);
        anotador1.setCartasDescartadas(Arrays.asList(1L, 2L));

        anotador2 = new Anotador();
        anotador2.setJugador(jugador2);
        anotador2.setPartida(partida1);
        anotador2.setCartasDescartadas(Arrays.asList(3L, 4L));

        entityManager.persistAndFlush(anotador1);
        entityManager.persistAndFlush(anotador2);
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar anotador por ID")
    void deberiaEncontrarAnotadorPorId() {
        Optional<Anotador> resultado = anotadorRepository.findById(anotador1.getId());

        assertTrue(resultado.isPresent());
        assertEquals(jugador1.getId(), resultado.get().getJugador().getId());
        assertEquals(partida1.getId(), resultado.get().getPartida().getId());
    }

    @Test
    @DisplayName("Debería guardar y recuperar anotador correctamente")
    void deberiaGuardarYRecuperarAnotador() {
        Anotador nuevo = new Anotador();
        nuevo.setJugador(jugador1);
        nuevo.setPartida(partida2);
        nuevo.setCartasDescartadas(Arrays.asList(5L));

        Anotador guardado = anotadorRepository.save(nuevo);
        entityManager.flush();
        entityManager.clear();

        Optional<Anotador> recuperado = anotadorRepository.findById(guardado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals(jugador1.getId(), recuperado.get().getJugador().getId());
        assertEquals(partida2.getId(), recuperado.get().getPartida().getId());
        assertEquals(Arrays.asList(5L), recuperado.get().getCartasDescartadas());
    }

    @Test
    @DisplayName("Debería encontrar todos los anotadores")
    void deberiaEncontrarTodosLosAnotadores() {
        List<Anotador> anotadores = anotadorRepository.findAll();

        assertNotNull(anotadores);
        assertEquals(2, anotadores.size());
    }

    @Test
    @DisplayName("Debería eliminar anotador correctamente")
    void deberiaEliminarAnotador() {
        Long id = anotador1.getId();
        assertTrue(anotadorRepository.existsById(id));

        anotadorRepository.deleteById(id);
        entityManager.flush();

        assertFalse(anotadorRepository.existsById(id));
    }

    @Test
    @DisplayName("Debería encontrar anotadores por partida ID")
    void deberiaEncontrarAnotadoresPorPartidaId() {
        List<Anotador> anotadores = anotadorRepository.findByPartidaId(partida1.getId());

        assertNotNull(anotadores);
        assertEquals(2, anotadores.size());
        assertTrue(anotadores.stream().allMatch(a -> a.getPartida().getId().equals(partida1.getId())));
    }

    @Test
    @DisplayName("Debería encontrar anotador por jugador ID y partida ID")
    void deberiaEncontrarAnotadorPorJugadorIdYPartidaId() {
        Optional<Anotador> resultado = anotadorRepository.findByJugadorIdAndPartidaId(jugador1.getId(), partida1.getId());

        assertTrue(resultado.isPresent());
        assertEquals(anotador1.getId(), resultado.get().getId());
    }

    @Test
    @DisplayName("Debería retornar vacío si no encuentra anotador por jugador y partida")
    void deberiaRetornarVacioSiNoEncuentraPorJugadorYPartida() {
        Optional<Anotador> resultado = anotadorRepository.findByJugadorIdAndPartidaId(jugador1.getId(), partida2.getId());

        assertFalse(resultado.isPresent());
    }
}
