package com.pds.partidosapp.service;

/**
 * Interfaz que define las operaciones disponibles para gestionar invitaciones.
 */
import com.pds.partidosapp.dto.InvitacionRequestDTO;
import com.pds.partidosapp.dto.InvitacionResponseDTO;


public interface IInvitacionService {

    /**
     * Crea una nueva invitación.
     * 
     * @param requestDTO Datos de la invitación a crear
     * @return DTO de la invitación creada
     */
    InvitacionResponseDTO crear(InvitacionRequestDTO requestDTO);
    

    /**
     * Elimina una invitación por su ID.
     * 
     * @param id ID de la invitación a eliminar
     */
    void eliminar(Long id);
    
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
