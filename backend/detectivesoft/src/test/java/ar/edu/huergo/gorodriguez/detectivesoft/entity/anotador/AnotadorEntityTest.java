package ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.anotador.Anotador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de Lógica Básica - Entidad Anotador")
class AnotadorEntityTest {

    @Test
    @DisplayName("Debería crear un anotador válido con builder")
    void deberiaCrearAnotadorValidoConBuilder() {
        Jugador jugador = new Jugador();
        jugador.setId(1L);
        jugador.setUsername("testUser");

        Partida partida = new Partida();
        partida.setId(1L);
        partida.setCodigo("ABC123");

        List<Long> cartasDescartadas = Arrays.asList(1L, 2L, 3L);

        Anotador anotador = Anotador.builder()
                .id(1L)
                .jugador(jugador)
                .partida(partida)
                .cartasDescartadas(cartasDescartadas)
                .build();

        assertNotNull(anotador);
        assertEquals(1L, anotador.getId());
        assertEquals(jugador, anotador.getJugador());
        assertEquals(partida, anotador.getPartida());
        assertEquals(cartasDescartadas, anotador.getCartasDescartadas());
    }

    @Test
    @DisplayName("Debería manejar getters y setters correctamente")
    void deberiaManejarGettersYSetters() {
        Anotador anotador = new Anotador();

        Jugador jugador = new Jugador();
        jugador.setId(2L);
        anotador.setJugador(jugador);

        Partida partida = new Partida();
        partida.setId(2L);
        anotador.setPartida(partida);

        List<Long> cartas = Arrays.asList(4L, 5L);
        anotador.setCartasDescartadas(cartas);

        assertEquals(jugador, anotador.getJugador());
        assertEquals(partida, anotador.getPartida());
        assertEquals(cartas, anotador.getCartasDescartadas());
    }

    @Test
    @DisplayName("Debería inicializar lista de cartas descartadas vacía")
    void deberiaInicializarListaCartasDescartadasVacia() {
        Anotador anotador = new Anotador();

        assertNotNull(anotador.getCartasDescartadas());
        assertTrue(anotador.getCartasDescartadas().isEmpty());
    }

    @Test
    @DisplayName("Debería agregar y remover cartas de la lista descartadas")
    void deberiaAgregarYRemoverCartasListaDescartadas() {
        Anotador anotador = new Anotador();
        List<Long> cartas = anotador.getCartasDescartadas();

        cartas.add(10L);
        cartas.add(20L);

        assertEquals(2, cartas.size());
        assertTrue(cartas.contains(10L));
        assertTrue(cartas.contains(20L));

        cartas.remove(10L);
        assertEquals(1, cartas.size());
        assertFalse(cartas.contains(10L));
    }

    @Test
    @DisplayName("Debería crear anotador con constructor vacío")
    void deberiaCrearAnotadorConConstructorVacio() {
        Anotador anotador = new Anotador();

        assertNotNull(anotador);
        assertNull(anotador.getId());
        assertNull(anotador.getJugador());
        assertNull(anotador.getPartida());
        assertNotNull(anotador.getCartasDescartadas());
        assertTrue(anotador.getCartasDescartadas().isEmpty());
    }
}
