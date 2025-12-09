package ar.edu.huergo.practicaParcial;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animales")
public class Refugiado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nombre;
    @Column(nullable = false)
    @NotBlank
    private String tipo;

    @Column(nullable = false)
    @NotNull
    private Integer edad;

    @Column(nullable = false)
    private Boolean adoptado = false;
}
