package com.pds.partidosapp.repository;

import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import com.pds.partidosapp.model.entity.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Invitacion.
 * Proporciona métodos para acceder y gestionar las invitaciones en la base de datos.
 */
@Repository
public interface InvitacionRepository extends JpaRepository<Invitacion, Long> {
    
    /**
     * Busca una invitación por ID de partido y ID de usuario.
     *
     * @param partidoId ID del partido
     * @param usuarioId ID del usuario
     * @return La invitación encontrada, si existe
     */
    Optional<Invitacion> findByIdPartidoAndIdUsuario(Long partidoId, Long usuarioId);
    
    /**
     * Busca todas las invitaciones de un partido específico.
     *
     * @param partidoId ID del partido
     * @return Lista de invitaciones para el partido
     */
    List<Invitacion> findByIdPartido(Long partidoId);
    
    /**
     * Busca todas las invitaciones de un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de invitaciones del usuario
     */
    List<Invitacion> findByIdUsuario(Long usuarioId);
    
    /**
     * Busca todas las invitaciones con un estado específico.
     *
     * @param estado Estado de la invitación
     * @return Lista de invitaciones con el estado especificado
     */
    List<Invitacion> findByEstadoInvitacion(EstadoInvitacionEnum estado);
    
    /**
     * Busca todas las invitaciones de un partido con un estado específico.
     *
     * @param partidoId ID del partido
     * @param estado Estado de la invitación
     * @return Lista de invitaciones del partido con el estado especificado
     */
    List<Invitacion> findByIdPartidoAndEstadoInvitacion(Long partidoId, EstadoInvitacionEnum estado);
    
    /**
     * Busca todas las invitaciones de un usuario con un estado específico.
     *
     * @param usuarioId ID del usuario
     * @param estado Estado de la invitación
     * @return Lista de invitaciones del usuario con el estado especificado
     */
    List<Invitacion> findByIdUsuarioAndEstadoInvitacion(Long usuarioId, EstadoInvitacionEnum estado);
}
