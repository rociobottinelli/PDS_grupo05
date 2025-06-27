package com.pds.partidosapp.controller;


import com.pds.partidosapp.dto.ComentarioDTO;
import com.pds.partidosapp.dto.ComentarioResponseDTO;
import com.pds.partidosapp.service.ComentarioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioServiceImpl comentarioService;

    @PostMapping("/{partidoId}")
    public ResponseEntity<String> guardar(
            @PathVariable Long partidoId,
            @RequestBody @Valid ComentarioDTO dto) {

        comentarioService.guardarComentario(partidoId, dto);
        return ResponseEntity.ok("Comentario guardado con éxito");
    }

    @GetMapping("/{partidoId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listar(@PathVariable Long partidoId) {
        return ResponseEntity.ok(comentarioService.listarComentariosDePartido(partidoId));
    }
}
