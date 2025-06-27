package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.dto.PartidoResponseDTO;
import com.pds.partidosapp.dto.UsuarioInfoDTO;
import com.pds.partidosapp.service.PartidoService;
import com.pds.partidosapp.shared.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;
    private final AuthUtils authUtils;

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
    public ResponseEntity<PartidoResponseDTO> aceptarPartido(@PathVariable Long id) {
        Long idUsuarioActual = authUtils.getCurrentUserId();
        partidoService.aceptarPartido(id, idUsuarioActual);
        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);
        return ResponseEntity.ok(partidoDetallado);
    }

    @GetMapping("/{id}/sugerencias")
    public ResponseEntity<List<UsuarioInfoDTO>> sugerirJugadores(
            @PathVariable Long id,
            @RequestParam String criterio) {

        Long idUsuarioActual = authUtils.getCurrentUserId();
        List<UsuarioInfoDTO> sugeridos = partidoService.sugerirJugadoresWithNames(id, criterio);
        return ResponseEntity.ok(sugeridos);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PartidoResponseDTO> cancelarPartido(@PathVariable Long id) {
        Long idUsuarioActual = authUtils.getCurrentUserId();
        partidoService.cancelarPartido(id, idUsuarioActual);
        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);
        return ResponseEntity.ok(partidoDetallado);
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<PartidoResponseDTO> iniciarPartido(@PathVariable Long id) {
        Long idUsuarioActual = authUtils.getCurrentUserId();
        partidoService.iniciarPartido(id, idUsuarioActual);
        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);
        return ResponseEntity.ok(partidoDetallado);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PartidoResponseDTO> finalizarPartido(@PathVariable Long id) {
        Long idUsuarioActual = authUtils.getCurrentUserId();
        partidoService.finalizarPartido(id, idUsuarioActual);
        PartidoResponseDTO partidoDetallado = partidoService.getPartidoWithDetails(id);
        return ResponseEntity.ok(partidoDetallado);
    }

}
