package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.DeporteDTO;
import com.pds.partidosapp.model.entity.Deporte;

import java.util.List;

public interface IDeporteService {
    Deporte crearDeporte(DeporteDTO dto);
    Deporte actualizarDeporte(Long id, DeporteDTO dto);
    void eliminarDeporte(Long id);
    List<Deporte> listarDeportes();
}
