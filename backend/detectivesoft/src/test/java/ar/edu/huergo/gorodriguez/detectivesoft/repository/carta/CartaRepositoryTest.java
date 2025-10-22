package ar.edu.huergo.gorodriguez.detectivesoft.repository.carta;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;

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
        carta1 = new Carta(null, "Cuchillo", Carta.TipoCarta.ARMA, null, null, null);
        carta2 = new Carta(null, "Señor Verde", Carta.TipoCarta.PERSONAJE, null, null, null);

        entityManager.persistAndFlush(carta1);
        entityManager.persistAndFlush(carta2);
    }

    @Test
    @DisplayName("Debería encontrar cartas por tipo")
    void deberiaEncontrarPorTipo() {
        List<Carta> armas = cartaRepository.findByTipo(Carta.TipoCarta.ARMA);
        assertEquals(1, armas.size());
        assertEquals("Cuchillo", armas.get(0).getNombre());
    }

    @Test
    @DisplayName("Debería encontrar todas las cartas sin partida")
    void deberiaEncontrarCartasSinPartida() {
        List<Carta> libres = cartaRepository.findByPartidaIsNull();
        assertEquals(2, libres.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía para tipo inexistente")
    void deberiaRetornarVacioParaTipoInexistente() {
        List<Carta> habitaciones = cartaRepository.findByTipo(Carta.TipoCarta.HABITACION);
        assertTrue(habitaciones.isEmpty());
    }
}
