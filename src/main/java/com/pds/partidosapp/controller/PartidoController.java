package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.dto.PartidoResponseDTO;
import com.pds.partidosapp.dto.UsuarioInfoDTO;
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
import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @PostMapping
    public ResponseEntity<PartidoResponseDTO> crearPartido(@Valid @RequestBody PartidoDTO partidoDTO) {
        PartidoDTO partidoCreado = partidoService.crearPartido(partidoDTO);
        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(partidoCreado.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(partidoDetallado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> getPartido(@PathVariable Long id) {
        PartidoResponseDTO partido = partidoService.getPartidoWithDetails(id);
        return ResponseEntity.ok(partido);
    }

    @PostMapping("/{id}/aceptar")
    public ResponseEntity<PartidoResponseDTO> aceptarPartido(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        Long idUsuarioActual = extractUserIdFromToken(authHeader);

        partidoService.aceptarPartido(id, idUsuarioActual);

        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);

        return ResponseEntity.ok(partidoDetallado);
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

    @GetMapping("/{id}/sugerencias")
    public ResponseEntity<List<UsuarioInfoDTO>> sugerirJugadores(
            @PathVariable Long id,
            @RequestParam String criterio,
            @RequestHeader("Authorization") String authHeader) {

        Long idUsuarioActual = extractUserIdFromToken(authHeader);

        List<UsuarioInfoDTO> sugeridos = partidoService.sugerirJugadoresWithNames(id, criterio);

        return ResponseEntity.ok(sugeridos);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PartidoResponseDTO> cancelarPartido(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        Long idUsuarioActual = extractUserIdFromToken(authHeader);

        partidoService.cancelarPartido(id, idUsuarioActual);

        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);

        return ResponseEntity.ok(partidoDetallado);
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<PartidoResponseDTO> iniciarPartido(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        Long idUsuarioActual = extractUserIdFromToken(authHeader);

        partidoService.iniciarPartido(id, idUsuarioActual);

        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);

        return ResponseEntity.ok(partidoDetallado);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PartidoResponseDTO> finalizarPartido(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        Long idUsuarioActual = extractUserIdFromToken(authHeader);

        partidoService.finalizarPartido(id, idUsuarioActual);

        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);

        return ResponseEntity.ok(partidoDetallado);
    }

}
