package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.service.PartidoService;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.config.JwtTokenProvider;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @PostMapping
    public ResponseEntity<PartidoDTO> crearPartido(@Valid @RequestBody PartidoDTO partidoDTO) {
        PartidoDTO partidoCreado = partidoService.crearPartido(partidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(partidoCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getPartido(@PathVariable Long id) {
        PartidoDTO partido = partidoService.getPartidoById(id);
        return ResponseEntity.ok(partido);
    }

    @PostMapping("/{id}/aceptar")
    public ResponseEntity<PartidoDTO> aceptarPartido(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        Long idUsuarioActual = extractUserIdFromToken(authHeader);

        PartidoDTO partidoActualizado = partidoService.aceptarPartido(id, idUsuarioActual);

        return ResponseEntity.ok(partidoActualizado);
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Long extractUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmail(token);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));

        return usuario.getId();
}
}

