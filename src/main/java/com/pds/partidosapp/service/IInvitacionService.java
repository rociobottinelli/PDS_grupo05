package com.pds.partidosapp.service;

/**
 * Interfaz que define las operaciones disponibles para gestionar invitaciones.
 */
import com.pds.partidosapp.dto.InvitacionRequestDTO;
import com.pds.partidosapp.dto.InvitacionResponseDTO;
import com.pds.partidosapp.model.entity.Invitacion;

import java.util.List;

public interface IInvitacionService {
    
    /**
     * Obtiene todas las invitaciones.
     * 
     * @return Lista de DTOs de invitaciones
     */
    List<InvitacionResponseDTO> listarTodas();
    
    /**
     * Busca una invitación por su ID.
     * 
     * @param id ID de la invitación a buscar
     * @return DTO de la invitación encontrada
     * @throws RuntimeException Si la invitación no existe
     */
    InvitacionResponseDTO buscarPorId(Long id);
    
    /**
     * Crea una nueva invitación.
     * 
     * @param requestDTO Datos de la invitación a crear
     * @return DTO de la invitación creada
     */
    InvitacionResponseDTO crear(InvitacionRequestDTO requestDTO);
    
    /**
     * Actualiza una invitación existente.
     * 
     * @param id ID de la invitación a actualizar
     * @param requestDTO Nuevos datos de la invitación
     * @return DTO de la invitación actualizada
     * @throws RuntimeException Si la invitación no existe
     */
    InvitacionResponseDTO actualizar(Long id, InvitacionRequestDTO requestDTO);
    
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
    
    /**
     * Busca invitaciones por ID de partido.
     * 
     * @param partidoId ID del partido
     * @return Lista de DTOs de invitaciones para el partido
     */
    List<InvitacionResponseDTO> buscarPorPartidoId(Long partidoId);
    
    /**
     * Busca invitaciones por ID de usuario.
     * 
     * @param usuarioId ID del usuario
     * @return Lista de DTOs de invitaciones para el usuario
     */
    List<InvitacionResponseDTO> buscarPorUsuarioId(Long usuarioId);
}
