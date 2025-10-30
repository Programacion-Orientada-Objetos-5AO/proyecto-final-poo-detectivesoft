package ar.edu.huergo.gorodriguez.detectivesoft.repository.turno;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;

@DataJpaTest
@DisplayName("Tests de Integración - TurnoRepository")
class TurnoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TurnoRepository turnoRepository;

    private Partida partida;
    private Jugador jugador;
    private Turno turno1, turno2;

    @BeforeEach
    void setUp() {
        partida = new Partida();
        partida.setCodigo("ABC123");
        partida.setEstado(Partida.EstadoPartida.PENDIENTE);

        jugador = new Jugador();
        jugador.setEmail("test@mail.com");
        jugador.setUsername("player1");
        jugador.setPassword("1234");

        entityManager.persist(partida);
        entityManager.persist(jugador);

        turno1 = new Turno(null, partida, jugador, 1, true, null, null);
        turno2 = new Turno(null, partida, jugador, 2, false, null, null);

        entityManager.persistAndFlush(turno1);
        entityManager.persistAndFlush(turno2);
    }

    @Test
    @DisplayName("Debería encontrar turnos por partida")
    void deberiaEncontrarPorPartida() {
        List<Turno> resultado = turnoRepository.findByPartida(partida);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Debería encontrar turnos por jugador")
    void deberiaEncontrarPorJugador() {
        List<Turno> resultado = turnoRepository.findByJugador(jugador);
        assertEquals(2, resultado.size());
    }
}
