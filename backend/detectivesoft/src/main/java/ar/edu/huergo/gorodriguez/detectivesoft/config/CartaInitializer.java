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
                        new Carta(null, "Señorita Scarlet", TipoCarta.PERSONAJE, "señorita_scarlet.png", null, null),
                        new Carta(null, "Coronel Mustard", TipoCarta.PERSONAJE, "coronel_mustard.png", null, null),
                        new Carta(null, "Profesor Plum", TipoCarta.PERSONAJE, "profesor_plum.png", null, null),
                        new Carta(null, "Señora Peacock", TipoCarta.PERSONAJE, "señora_peacock.png", null, null),
                        new Carta(null, "Señor Green", TipoCarta.PERSONAJE, "señor_green.png", null, null),
                        new Carta(null, "Señora White", TipoCarta.PERSONAJE, "señora_white.png", null, null)
                );

                // ARMAS
                List<Carta> armas = List.of(
                        new Carta(null, "Candelabro", TipoCarta.ARMA, "candelabro.png", null, null),
                        new Carta(null, "Cuchillo", TipoCarta.ARMA, "cuchillo.png", null, null),
                        new Carta(null, "Tuberia de Plomo", TipoCarta.ARMA, "tuberia.png", null, null),
                        new Carta(null, "Pistola", TipoCarta.ARMA, "pistola.png", null, null),
                        new Carta(null, "Cuerda", TipoCarta.ARMA, "cuerda.png", null, null),
                        new Carta(null, "Llave Inglesa", TipoCarta.ARMA, "llave.png", null, null)
                );

                // HABITACIONES
                List<Carta> habitaciones = List.of(
                        new Carta(null, "Cocina", TipoCarta.HABITACION, "estudio.png", null, null),
                        new Carta(null, "Comedor", TipoCarta.HABITACION, "entrada.png", null, null),
                        new Carta(null, "Biblioteca", TipoCarta.HABITACION, "comedor.png", null, null),
                        new Carta(null, "Sala de Baile", TipoCarta.HABITACION, "sala_estar.png", null, null),
                        new Carta(null, "Salon", TipoCarta.HABITACION, "cuarto_individual.png", null, null),
                        new Carta(null, "Invernadero", TipoCarta.HABITACION, "cuarto_matrimonial.png", null, null),
                        new Carta(null, "Estudio", TipoCarta.HABITACION, "bano.png", null, null),
                        new Carta(null, "Billar", TipoCarta.HABITACION, "cocina.png", null, null),
                        new Carta(null, "Vestibulo", TipoCarta.HABITACION, "garaje.png", null, null)
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
