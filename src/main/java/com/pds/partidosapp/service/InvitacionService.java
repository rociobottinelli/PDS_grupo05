package com.pds.partidosapp.service;

import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import com.pds.partidosapp.model.entity.Invitacion;
import com.pds.partidosapp.repository.InvitacionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para gestionar invitaciones.
 * Proporciona métodos para aceptar y cancelar invitaciones.
 */
@Service
@RequiredArgsConstructor
public class InvitacionService implements IInvitacionService {

    private final InvitacionRepository invitacionRepository;

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
}
