package ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta.TipoCarta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida.EstadoPartida;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.jugador.JugadorRepository;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.partida.PartidaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Tests de Integración - AcusacionRepository")
class AcusacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AcusacionRepository acusacionRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private CartaRepository cartaRepository;

    private Acusacion acusacion1;
    private Acusacion acusacion2;
    private Partida partida;
    private Jugador jugador1;
    private Jugador jugador2;
    private Carta personaje;
    private Carta arma;
    private Carta habitacion;

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

        personaje = new Carta();
        personaje.setNombre("Profesor Plum");
        personaje.setTipo(TipoCarta.PERSONAJE);
        entityManager.persistAndFlush(personaje);

        arma = new Carta();
        arma.setNombre("Cuchillo");
        arma.setTipo(TipoCarta.ARMA);
        entityManager.persistAndFlush(arma);

        habitacion = new Carta();
        habitacion.setNombre("Biblioteca");
        habitacion.setTipo(TipoCarta.HABITACION);
        entityManager.persistAndFlush(habitacion);

        // Crear acusaciones
        acusacion1 = new Acusacion();
        acusacion1.setPartida(partida);
        acusacion1.setJugador(jugador1);
        acusacion1.setPersonaje(personaje);
        acusacion1.setArma(arma);
        acusacion1.setHabitacion(habitacion);
        acusacion1.setFecha(LocalDateTime.now());
        acusacion1.setCorrecta(false);

        acusacion2 = new Acusacion();
        acusacion2.setPartida(partida);
        acusacion2.setJugador(jugador2);
        acusacion2.setPersonaje(personaje);
        acusacion2.setArma(arma);
        acusacion2.setHabitacion(habitacion);
        acusacion2.setFecha(LocalDateTime.now().plusMinutes(5));
        acusacion2.setCorrecta(true);

        entityManager.persistAndFlush(acusacion1);
        entityManager.persistAndFlush(acusacion2);
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar acusacion por ID")
    void deberiaEncontrarAcusacionPorId() {
        var resultado = acusacionRepository.findById(acusacion1.getId());

        assertTrue(resultado.isPresent());
        assertFalse(resultado.get().getCorrecta());
    }

    @Test
    @DisplayName("Debería guardar y recuperar acusacion correctamente")
    void deberiaGuardarYRecuperarAcusacion() {
        Acusacion nueva = new Acusacion();
        nueva.setPartida(partida);
        nueva.setJugador(jugador1);
        nueva.setPersonaje(personaje);
        nueva.setArma(arma);
        nueva.setHabitacion(habitacion);
        nueva.setFecha(LocalDateTime.now());
        nueva.setCorrecta(false);

        Acusacion guardada = acusacionRepository.save(nueva);
        entityManager.flush();
        entityManager.clear();

        var recuperada = acusacionRepository.findById(guardada.getId());
        assertTrue(recuperada.isPresent());
        assertFalse(recuperada.get().getCorrecta());
    }

    @Test
    @DisplayName("Debería encontrar acusaciones por partida")
    void deberiaEncontrarAcusacionesPorPartida() {
        List<Acusacion> acusaciones = acusacionRepository.findByPartida(partida);

        assertNotNull(acusaciones);
        assertEquals(2, acusaciones.size());
        assertTrue(acusaciones.stream().allMatch(a -> a.getPartida().getId().equals(partida.getId())));
    }

    @Test
    @DisplayName("Debería encontrar acusaciones por jugador")
    void deberiaEncontrarAcusacionesPorJugador() {
        List<Acusacion> acusaciones = acusacionRepository.findByJugador(jugador1);

        assertNotNull(acusaciones);
        assertEquals(1, acusaciones.size());
        assertEquals(jugador1.getId(), acusaciones.get(0).getJugador().getId());
    }

    @Test
    @DisplayName("Debería encontrar acusaciones por partida y jugador")
    void deberiaEncontrarAcusacionesPorPartidaYJugador() {
        List<Acusacion> acusaciones = acusacionRepository.findByPartidaAndJugador(partida, jugador2);

        assertNotNull(acusaciones);
        assertEquals(1, acusaciones.size());
        assertEquals(jugador2.getId(), acusaciones.get(0).getJugador().getId());
    }

    @Test
    @DisplayName("Debería encontrar acusaciones correctas por partida")
    void deberiaEncontrarAcusacionesCorrectasPorPartida() {
        List<Acusacion> correctas = acusacionRepository.findByPartidaAndCorrectaTrue(partida);

        assertNotNull(correctas);
        assertEquals(1, correctas.size());
        assertTrue(correctas.get(0).getCorrecta());
    }

    @Test
    @DisplayName("Debería eliminar acusacion correctamente")
    void deberiaEliminarAcusacion() {
        Long id = acusacion1.getId();
        assertTrue(acusacionRepository.existsById(id));

        acusacionRepository.deleteById(id);
        entityManager.flush();

        assertFalse(acusacionRepository.existsById(id));
    }
}
