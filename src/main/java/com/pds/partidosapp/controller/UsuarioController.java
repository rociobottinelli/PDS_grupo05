package com.pds.partidosapp.controller;

import com.pds.partidosapp.config.JwtTokenProvider;
import com.pds.partidosapp.dto.AddDeporteToUsuarioDTO;
import com.pds.partidosapp.dto.RegisterDTO;
import com.pds.partidosapp.dto.UpdateUsuarioDTO;
import com.pds.partidosapp.dto.UsuarioResponseDTO;
import com.pds.partidosapp.enums.NivelEnum;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.service.IUsuarioService;
import com.pds.partidosapp.shared.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final IUsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(
            @RequestParam(required = false) NivelEnum nivel,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax,
            @RequestParam(required = false) Long ubicacionId) {

        List<UsuarioResponseDTO> usuarios = usuarioService.findUsuarios(nivel, edadMin, edadMax, ubicacionId);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.getUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateUsuarioDTO dto) {
        usuarioService.updateUser(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/me/deportes")
    public ResponseEntity<Void> agregarDeporteAlUsuarioActual(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid AddDeporteToUsuarioDTO dto) {
        // Obtener el ID del usuario autenticado
        Long idUsuarioActual = extractUserIdFromToken(authHeader);
        Usuario usuario = usuarioRepository.findById(idUsuarioActual)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        
        // Llamar al servicio para agregar el deporte
        usuarioService.agregarDeporteAUsuario(
                usuario.getId(),
                dto.getDeporteId(),
                dto.getNivelEnDeporte()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    private Long extractUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmail(token);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));

        return usuario.getId();
    }
}
