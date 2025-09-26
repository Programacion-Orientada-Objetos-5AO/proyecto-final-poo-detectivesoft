package ar.edu.huergo.gorodriguez.detectivesoft.service.cartas;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.huergo.gorodriguez.detectivesoft.entity.cartas.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.cartas.Carta.TipoCarta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.jugador.Jugador;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.partida.Partida;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.cartas.CartaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaService {
    private final CartaRepository cartaRepository;

    public List<Carta> getAllCartas() {
        return cartaRepository.findAll();
    }

    public Carta getCartaById(Long id) {
        return cartaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carta con id " + id + " no encontrada"));
    }

    public Carta crearCarta(Carta carta) {
        return cartaRepository.save(carta);
    }

    public Carta actualizarCarta(Long id, Carta datos) {
        Carta existente = getCartaById(id);
        existente.setNombre(datos.getNombre());
        existente.setTipo(datos.getTipo());
        existente.setPartida(datos.getPartida());
        existente.setJugador(datos.getJugador());
        return cartaRepository.save(existente);
    }

    public void borrarCarta(Long id) {
        Carta carta = getCartaById(id);
        cartaRepository.delete(carta);
    }

    public List<Carta> getCartasByPartida(Partida partida) {
        return cartaRepository.findByPartida(partida);
    }

    public List<Carta> getCartasByJugador(Jugador jugador) {
        return cartaRepository.findByJugador(jugador);
    }

    public List<Carta> getCartasByTipo(TipoCarta tipo) {
        return cartaRepository.findByTipo(tipo);
    }
}