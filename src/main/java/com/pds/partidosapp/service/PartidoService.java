package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.PartidoDTO;

public interface PartidoService {
    PartidoDTO crearPartido(PartidoDTO partidoDTO);
    PartidoDTO getPartidoById(Long id);
    PartidoDTO aceptarPartido(Long partidoId, Long idUsuarioActual);
}