package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}

