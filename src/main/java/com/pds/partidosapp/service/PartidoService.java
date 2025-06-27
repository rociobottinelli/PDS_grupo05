package com.pds.partidosapp.service;

import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.dto.PartidoResponseDTO;
import com.pds.partidosapp.dto.UsuarioInfoDTO;
import java.util.List;

public interface PartidoService {
    PartidoDTO crearPartido(PartidoDTO partidoDTO);

    PartidoDTO getPartidoById(Long id);

    PartidoDTO aceptarPartido(Long partidoId, Long idUsuarioActual);

    PartidoDTO cancelarPartido(Long partidoId, Long idUsuarioActual);

    PartidoDTO iniciarPartido(Long partidoId, Long idUsuarioActual);

    PartidoDTO finalizarPartido(Long partidoId, Long idUsuarioActual);

    List<Usuario> sugerirJugadores(Long partidoId, String criterio);

    PartidoResponseDTO getPartidoWithDetails(Long id);

    List<UsuarioInfoDTO> sugerirJugadoresWithNames(Long partidoId, String criterio);
}