package ar.edu.huergo.gorodriguez.detectivesoft.repository.carta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta.TipoCarta;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Tests de Integración - CartaRepository")
class CartaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartaRepository cartaRepository;

    private Carta carta1;
    private Carta carta2;

    @BeforeEach
    void setUp() {
        carta1 = new Carta();
        carta1.setNombre("Cuchillo");
        carta1.setTipo(TipoCarta.ARMA);

        carta2 = new Carta();
        carta2.setNombre("Biblioteca");
        carta2.setTipo(TipoCarta.HABITACION);

        entityManager.persistAndFlush(carta1);
        entityManager.persistAndFlush(carta2);
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar carta por ID")
    void deberiaEncontrarCartaPorId() {
        Optional<Carta> resultado = cartaRepository.findById(carta1.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Cuchillo", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar carta correctamente")
    void deberiaGuardarYRecuperarCarta() {
        Carta nueva = new Carta();
        nueva.setNombre("Profesor Plum");
        nueva.setTipo(TipoCarta.PERSONAJE);

        Carta guardada = cartaRepository.save(nueva);
        entityManager.flush();
        entityManager.clear();

        Optional<Carta> recuperada = cartaRepository.findById(guardada.getId());
        assertTrue(recuperada.isPresent());
        assertEquals("Profesor Plum", recuperada.get().getNombre());
    }

    @Test
    @DisplayName("Debería encontrar todas las cartas")
    void deberiaEncontrarTodasLasCartas() {
        List<Carta> cartas = cartaRepository.findAll();

        assertNotNull(cartas);
        assertEquals(2, cartas.size());
    }

    @Test
    @DisplayName("Debería eliminar carta correctamente")
    void deberiaEliminarCarta() {
        Long id = carta1.getId();
        assertTrue(cartaRepository.existsById(id));

        cartaRepository.deleteById(id);
        entityManager.flush();

        assertFalse(cartaRepository.existsById(id));
    }
}
