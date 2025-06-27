package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.ComentarioDTO;
import com.pds.partidosapp.dto.ComentarioResponseDTO;

import java.util.List;

public interface IComentarioService {
    void guardarComentario(Long partidoId, ComentarioDTO dto);
    List<ComentarioResponseDTO> listarComentariosDePartido(Long partidoId);
}
