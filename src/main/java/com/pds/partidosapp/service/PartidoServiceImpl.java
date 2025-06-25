package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.PartidoRepository;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.service.PartidoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public PartidoDTO crearPartido(PartidoDTO partidoDTO) {
        Usuario organizador = usuarioRepository.findById(partidoDTO.getOrganizadorId())
                .orElseThrow(() -> new EntityNotFoundException("Organizador no encontrado con ID: " + partidoDTO.getOrganizadorId()));

        Partido partido = Partido.builder()
                .organizador(organizador)
                .estado(partidoDTO.getEstado())
                .fechaHora(partidoDTO.getFechaHora())
                .jugadoresRequeridos(partidoDTO.getJugadoresRequeridos())
                .jugadores(new ArrayList<>())  // Inicializamos vacío, después podemos agregar el organizador si corresponde
                .build();

        // Opcional: agregar el organizador como primer jugador automáticamente
        partido.getJugadores().add(organizador);

        Partido partidoGuardado = partidoRepository.save(partido);

        return mapToDTO(partidoGuardado);
    }

    @Override
    public PartidoDTO getPartidoById(Long id) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + id));

        return mapToDTO(partido);
    }

    private PartidoDTO mapToDTO(Partido partido) {
        return PartidoDTO.builder()
                .id(partido.getId())
                .organizadorId(partido.getOrganizador() != null ? partido.getOrganizador().getId() : null)
                .estado(partido.getEstado())
                .fechaHora(partido.getFechaHora())
                .ubicacionId(partido.getUbicacion() != null ? partido.getUbicacion().getId() : null)
                .deporteId(partido.getDeporte() != null ? partido.getDeporte().getId() : null)
                .jugadoresRequeridos(partido.getJugadoresRequeridos())
                .jugadoresIds(partido.getJugadores() != null
                        ? partido.getJugadores().stream().map(j -> j.getId()).toList()
                        : null)
                .build();
}

}

