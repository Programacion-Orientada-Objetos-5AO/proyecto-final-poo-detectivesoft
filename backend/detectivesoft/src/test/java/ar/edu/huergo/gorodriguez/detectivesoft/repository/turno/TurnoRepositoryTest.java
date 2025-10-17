package ar.edu.huergo.gorodriguez.detectivesoft.repository.turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.turno.Turno;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Tests de Integración - TurnoRepository")
class TurnoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    private Turno turno1;
    private Turno turno2;
    private Partida partida;
    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        // Crear entidades relacionadas
        partida = new Partida();
        partida.setCodigo("ABC123");
        partida.setEstado(EstadoPartida.EN_CURSO);
        partida.setMaxJugadores(4);
        partida.setFechaCreacion(LocalDateTime.now());
        entityManager.persistAndFlush(partida);

        jugador1 = new Jugador("jugador1@test.com", "jugador1", "pass1");
        jugador2 = new Jugador("jugador2@test.com", "jugador2", "pass2");
        entityManager.persistAndFlush(jugador1);
        entityManager.persistAndFlush(jugador2);

        // Crear turnos
        turno1 = new Turno();
        turno1.setPartida(partida);
        turno1.setJugador(jugador1);
        turno1.setNumeroTurno(1);
        turno1.setActivo(true);
        turno1.setFechaInicio(LocalDateTime.now());

        turno2 = new Turno();
        turno2.setPartida(partida);
        turno2.setJugador(jugador2);
        turno2.setNumeroTurno(2);
        turno2.setActivo(false);
        turno2.setFechaInicio(LocalDateTime.now().minusMinutes(5));
        turno2.setFechaFin(LocalDateTime.now());

        entityManager.persistAndFlush(turno1);
        entityManager.persistAndFlush(turno2);
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar turno por ID")
    void deberiaEncontrarTurnoPorId() {
        var resultado = turnoRepository.findById(turno1.getId());

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getNumeroTurno());
        assertTrue(resultado.get().isActivo());
    }

    @Test
    @DisplayName("Debería guardar y recuperar turno correctamente")
    void deberiaGuardarYRecuperarTurno() {
        Turno nuevo = new Turno();
        nuevo.setPartida(partida);
        nuevo.setJugador(jugador1);
        nuevo.setNumeroTurno(3);
        nuevo.setActivo(true);
        nuevo.setFechaInicio(LocalDateTime.now());

        Turno guardado = turnoRepository.save(nuevo);
        entityManager.flush();
        entityManager.clear();

        var recuperado = turnoRepository.findById(guardado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals(3, recuperado.get().getNumeroTurno());
    }

    @Test
    @DisplayName("Debería encontrar turnos por partida")
    void deberiaEncontrarTurnosPorPartida() {
        List<Turno> turnos = turnoRepository.findByPartida(partida);

        assertNotNull(turnos);
        assertEquals(2, turnos.size());
        assertTrue(turnos.stream().allMatch(t -> t.getPartida().getId().equals(partida.getId())));
    }

    @Test
    @DisplayName("Debería encontrar turnos por jugador")
    void deberiaEncontrarTurnosPorJugador() {
        List<Turno> turnos = turnoRepository.findByJugador(jugador1);

        assertNotNull(turnos);
        assertEquals(1, turnos.size());
        assertEquals(jugador1.getId(), turnos.get(0).getJugador().getId());
    }

    @Test
    @DisplayName("Debería eliminar turno correctamente")
    void deberiaEliminarTurno() {
        Long id = turno1.getId();
        assertTrue(turnoRepository.existsById(id));

        turnoRepository.deleteById(id);
        entityManager.flush();

        assertFalse(turnoRepository.existsById(id));
    }
}
