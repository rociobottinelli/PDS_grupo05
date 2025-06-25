package com.pds.partidosapp.service;

/**
 * Interfaz que define las operaciones disponibles para gestionar invitaciones.
 */
public interface IInvitacionService {
    
    /**
     * Acepta una invitación cambiando su estado a ACEPTADO.
     * 
     * @param idInvitacion ID de la invitación a aceptar
     * @throws RuntimeException Si la invitación no existe o no se puede actualizar
     */
    void aceptar(Long idInvitacion);
    
    /**
     * Rechaza una invitación cambiando su estado a RECHAZADO.
     * 
     * @param idInvitacion ID de la invitación a rechazar
     * @throws RuntimeException Si la invitación no existe o no se puede actualizar
     */
    void cancelar(Long idInvitacion);
}
