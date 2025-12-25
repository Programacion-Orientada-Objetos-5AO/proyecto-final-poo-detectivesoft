package ar.edu.huergo.gorodriguez.detectivesoft.repository.anotador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@DataJpaTest
@DisplayName("Tests de Integración - AnotadorRepository")
class AnotadorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnotadorRepository anotadorRepository;

    private Jugador jugador;
    private Partida partida;
    private Anotador anotador;

    @BeforeEach
    void setUp() {
        jugador = new Jugador();
        jugador.setUsername("player1");
        jugador.setEmail("test@mail.com");
        jugador.setPassword("1234");

        partida = new Partida();
        partida.setCodigo("PART01");
        partida.setEstado(Partida.EstadoPartida.PENDIENTE);

        entityManager.persist(jugador);
        entityManager.persist(partida);

        anotador = Anotador.builder()
                .jugador(jugador)
                .partida(partida)
                .build();

        entityManager.persistAndFlush(anotador);
    }

    @Test
    @DisplayName("Debería encontrar anotador por jugador y partida")
    void deberiaEncontrarPorJugadorYPartida() {
        Optional<Anotador> encontrado = anotadorRepository.findByJugadorIdAndPartidaId(jugador.getId(), partida.getId());
        assertTrue(encontrado.isPresent());
    }

    @Test
    @DisplayName("Debería listar anotadores por partida")
    void deberiaListarPorPartida() {
        List<Anotador> lista = anotadorRepository.findByPartidaId(partida.getId());
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Debería encontrar por jugador ID")
    void deberiaEncontrarPorJugadorId() {
        Optional<Anotador> encontrado = anotadorRepository.findByJugadorId(jugador.getId());
        assertTrue(encontrado.isPresent());
    }
}
