package ar.edu.huergo.gorodriguez.detectivesoft.controller.security;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.gorodriguez.detectivesoft.dto.security.LoginDto;
import ar.edu.huergo.gorodriguez.detectivesoft.service.security.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDto request) {
        // 1) Autenticar credenciales con email/password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        // 2) Cargar UserDetails usando el email
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        // 3) Extraer roles
        List<String> roles = userDetails.getAuthorities()
                                        .stream()
                                        .map(a -> a.getAuthority())
                                        .toList();

        // 4) Generar token con el email como subject
        String token = jwtTokenService.generarToken(userDetails, roles);

        // 5) Devolver token
        return ResponseEntity.ok(Map.of("token", token));
    }
}
