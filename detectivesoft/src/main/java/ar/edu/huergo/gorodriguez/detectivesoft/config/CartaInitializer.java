package ar.edu.huergo.gorodriguez.detectivesoft.config;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta;
import ar.edu.huergo.gorodriguez.detectivesoft.entity.carta.Carta.TipoCarta;
import ar.edu.huergo.gorodriguez.detectivesoft.repository.carta.CartaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CartaInitializer {

    @Bean
    CommandLineRunner initCartas(CartaRepository cartaRepository) {
        return args -> {
            if (cartaRepository.count() == 0) {
                log.info("Inicializando cartas del juego Detectivesoft...");

                // PERSONAJES
                List<Carta> personajes = List.of(
                        new Carta(null, "Jack Brader", TipoCarta.PERSONAJE, "jack_brader.png", null, null),
                        new Carta(null, "Alice Nurse", TipoCarta.PERSONAJE, "alice_nurse.png", null, null),
                        new Carta(null, "Calvin Schwager", TipoCarta.PERSONAJE, "calvin_schwager.png", null, null),
                        new Carta(null, "Alfred Butler", TipoCarta.PERSONAJE, "alfred_butler.png", null, null),
                        new Carta(null, "Sofia Wife", TipoCarta.PERSONAJE, "sofia_wife.png", null, null),
                        new Carta(null, "Alba Daughter", TipoCarta.PERSONAJE, "alba_daughter.png", null, null)
                );

                // ARMAS
                List<Carta> armas = List.of(
                        new Carta(null, "Martillo", TipoCarta.ARMA, "martillo.png", null, null),
                        new Carta(null, "Veneno", TipoCarta.ARMA, "veneno.png", null, null),
                        new Carta(null, "Pistola", TipoCarta.ARMA, "pistola.png", null, null),
                        new Carta(null, "Cuerda", TipoCarta.ARMA, "cuerda.png", null, null),
                        new Carta(null, "Hacha", TipoCarta.ARMA, "hacha.png", null, null),
                        new Carta(null, "Cuchillo", TipoCarta.ARMA, "cuchillo.png", null, null)
                );

                // HABITACIONES
                List<Carta> habitaciones = List.of(
                        new Carta(null, "Estudio", TipoCarta.HABITACION, "estudio.png", null, null),
                        new Carta(null, "Entrada", TipoCarta.HABITACION, "entrada.png", null, null),
                        new Carta(null, "Comedor", TipoCarta.HABITACION, "comedor.png", null, null),
                        new Carta(null, "Sala de Estar", TipoCarta.HABITACION, "sala_estar.png", null, null),
                        new Carta(null, "Cuarto Individual", TipoCarta.HABITACION, "cuarto_individual.png", null, null),
                        new Carta(null, "Cuarto Matrimonial", TipoCarta.HABITACION, "cuarto_matrimonial.png", null, null),
                        new Carta(null, "Baño", TipoCarta.HABITACION, "bano.png", null, null),
                        new Carta(null, "Cocina", TipoCarta.HABITACION, "cocina.png", null, null),
                        new Carta(null, "Garaje", TipoCarta.HABITACION, "garaje.png", null, null)
                );

                cartaRepository.saveAll(personajes);
                cartaRepository.saveAll(armas);
                cartaRepository.saveAll(habitaciones);

                log.info("Cartas iniciales cargadas correctamente con imágenes.");
            } else {
                log.info("Las cartas ya estaban cargadas, no se realizó inicialización.");
            }
        };
    }
}
