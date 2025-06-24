package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.DeporteDTO;
import com.pds.partidosapp.model.entity.Deporte;
import com.pds.partidosapp.service.DeporteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deportes")
@RequiredArgsConstructor
public class DeporteController {

    @Autowired
    private final DeporteServiceImpl service;

    @PostMapping
    public Deporte crear(@RequestBody @Valid DeporteDTO dto) {
        return service.crearDeporte(dto);
    }

    @PutMapping("/{id}")
    public Deporte modificar(@PathVariable Long id, @RequestBody @Valid  DeporteDTO dto) {
        return service.actualizarDeporte(id, dto);
    }

    @GetMapping
    public List<Deporte> listar() {
        return service.listarDeportes();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarDeporte(id);
    }
}