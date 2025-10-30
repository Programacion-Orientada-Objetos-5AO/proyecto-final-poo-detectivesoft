package ar.edu.huergo.gorodriguez.detectivesoft.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginDto(
        @NotBlank(message = "El email es requerido")
        @Email(message = "Debe ser un email válido") 
        String email,

        @NotBlank(message = "La contraseña es requerida") 
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial"
        )
        String password
) {}

