package ar.edu.huergo.gorodriguez.detectivesoft.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistrarDto(
        @NotBlank(message = "El email es requerido")
        @Email(message = "Debe ser un email válido") 
        String email,

        @NotBlank(message = "El nombre de usuario es requerido")
        String username,

        @NotBlank(message = "La contraseña es requerida") 
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,}$",
            message = "La contraseña debe tener al menos 16 caracteres, una mayúscula, una minúscula, un número y un carácter especial"
        )
        String password,

        @NotBlank(message = "La verificación de contraseña es requerida")
        String verificacionPassword
) {}


