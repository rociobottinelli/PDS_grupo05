package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.LoginDTO;
import com.pds.partidosapp.dto.RegisterDTO;
import com.pds.partidosapp.dto.UsuarioResponseDTO;
import com.pds.partidosapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO request) {
        String token = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("partidos-token", token)
                .httpOnly(true)
                .secure(false) // True en producción con HTTPS
                .path("/")
                .maxAge(86400) // 1 día
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Login exitoso"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("partidos-token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0) // Expira inmediatamente
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Logout exitoso"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        UsuarioResponseDTO usuario = authService.getProfile(email);

        return ResponseEntity.ok(Map.of(
                "id", usuario.getId(),
                "email", usuario.getEmail(),
                "nombreUsuario", usuario.getNombreUsuario(),
                "nivel", usuario.getNivelJuego(),
                "edad", usuario.getEdad()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO request) {
        String token = authService.register(request);

        ResponseCookie cookie = ResponseCookie.from("partidos-token", token)
                .httpOnly(true)
                .secure(false) // True en producción con HTTPS
                .path("/")
                .maxAge(86400) // 1 día
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Registro exitoso"));
    }

}
