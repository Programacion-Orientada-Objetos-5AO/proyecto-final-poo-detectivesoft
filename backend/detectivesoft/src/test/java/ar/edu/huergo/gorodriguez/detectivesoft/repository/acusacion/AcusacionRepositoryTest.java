package ar.edu.huergo.gorodriguez.detectivesoft.repository.acusacion;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.acusacion.Acusacion;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;

@DataJpaTest
@DisplayName("Tests de Integración - AcusacionRepository")
class AcusacionRepositoryTest {

    @Autowired
    private AcusacionRepository acusacionRepository;

    @Test
    @DisplayName("Debería guardar y recuperar una acusación correctamente")
    void deberiaGuardarYRecuperarAcusacion() {
        Partida partida = new Partida();
        Jugador jugador = new Jugador();
        Carta carta = new Carta();

        Acusacion acusacion = new Acusacion(null, partida, jugador, carta, carta, carta, null, false);
        acusacionRepository.save(acusacion);

        List<Acusacion> lista = acusacionRepository.findAll();
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Debería devolver lista vacía si no hay acusaciones correctas")
    void deberiaDevolverListaVaciaSiNoHayCorrectas() {
        Partida partida = new Partida();
        List<Acusacion> resultado = acusacionRepository.findByPartidaAndCorrectaTrue(partida);
        assertTrue(resultado.isEmpty());
    }
}
