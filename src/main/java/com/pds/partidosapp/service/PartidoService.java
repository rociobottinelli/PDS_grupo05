package com.pds.partidosapp.service;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.dto.PartidoDTO;
import java.util.List;

public interface PartidoService {
    PartidoDTO crearPartido(PartidoDTO partidoDTO);
    PartidoDTO getPartidoById(Long id);
    PartidoDTO aceptarPartido(Long partidoId, Long idUsuarioActual);
    List<Usuario> sugerirJugadores(Long partidoId, String criterio);
}