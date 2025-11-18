package ar.edu.huergo.gorodriguez.detectivesoft.calculadoraParcial.entity;

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
@Table(name = "Calculadora")
public class Calculadora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double parametro1;

    @NotNull
    @Column(nullable = false)
    private Double parametro2;

    @NotNull
    @Column(nullable = false)
    private String operacion;

    private Double resultado;
}
