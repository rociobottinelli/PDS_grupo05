package com.pds.partidosapp.service;

import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import com.pds.partidosapp.model.entity.Invitacion;
import com.pds.partidosapp.repository.InvitacionRepository;
import com.pds.partidosapp.dto.InvitacionRequestDTO;
import com.pds.partidosapp.dto.InvitacionResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para gestionar las operaciones relacionadas con las invitaciones.
 * Proporciona métodos para crear, leer, actualizar y eliminar invitaciones,
 * así como para aceptar o rechazar invitaciones existentes.
 */
@Service
@RequiredArgsConstructor
public class InvitacionService implements IInvitacionService {

    private final InvitacionRepository invitacionRepository;


    @Override
    @Transactional
    public InvitacionResponseDTO crear(InvitacionRequestDTO requestDTO) {
        // Verificar si ya existe una invitación para este partido y usuario
        invitacionRepository.findByIdPartidoAndIdUsuario(
                requestDTO.getPartidoId(), 
                requestDTO.getUsuarioId()
        ).ifPresent(invitacion -> {
            throw new RuntimeException("Ya existe una invitación para este partido y usuario");
        });

        Invitacion invitacion = Invitacion.builder()
                .idPartido(requestDTO.getPartidoId())
                .idUsuario(requestDTO.getUsuarioId())
                .estadoInvitacion(EstadoInvitacionEnum.PENDIENTE)
                .build();

        Invitacion invitacionGuardada = invitacionRepository.save(invitacion);
        return convertirADTO(invitacionGuardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Invitacion invitacion = buscarInvitacionPorId(id);
        invitacionRepository.delete(invitacion);
    }

    @Override
    @Transactional
    public void aceptar(Long idInvitacion) {
        Invitacion invitacion = buscarInvitacionPorId(idInvitacion);
        invitacion.setEstadoInvitacion(EstadoInvitacionEnum.ACEPTADO);
        invitacionRepository.save(invitacion);
    }

    @Override
    @Transactional
    public void cancelar(Long idInvitacion) {
        Invitacion invitacion = buscarInvitacionPorId(idInvitacion);
        invitacion.setEstadoInvitacion(EstadoInvitacionEnum.RECHAZADO);
        invitacionRepository.save(invitacion);
    }

    /**
     * Busca una invitación por su ID.
     * 
     * @param id ID de la invitación a buscar
     * @return La invitación encontrada
     * @throws RuntimeException Si la invitación no existe
     */
    private Invitacion buscarInvitacionPorId(Long id) {
        return invitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la invitación con ID: " + id));
    }
    
    /**
     * Convierte una entidad Invitacion a su DTO correspondiente.
     * 
     * @param invitacion La entidad Invitacion a convertir
     * @return El DTO de la invitación
     */
    private InvitacionResponseDTO convertirADTO(Invitacion invitacion) {
        return InvitacionResponseDTO.builder()
                .id(invitacion.getId())
                .partidoId(invitacion.getIdPartido())
                .usuarioId(invitacion.getIdUsuario())
                .estadoInvitacion(invitacion.getEstadoInvitacion())
                .fechaCreacion(invitacion.getFechaCreacion())
                .fechaActualizacion(invitacion.getFechaActualizacion())
                .build();
    }
}
