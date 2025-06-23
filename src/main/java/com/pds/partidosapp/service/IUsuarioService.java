package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.RegisterDTO;
import com.pds.partidosapp.dto.UpdateUsuarioDTO;
import com.pds.partidosapp.dto.UsuarioResponseDTO;
import com.pds.partidosapp.enums.NivelEnum;

import java.util.List;

public interface IUsuarioService {

    // CRUD básico esencial
    UsuarioResponseDTO getUsuario(Long id);

    UsuarioResponseDTO createUser(RegisterDTO registerDTO);

    void updateUser(Long id, UpdateUsuarioDTO updateUsuarioDTO);

    void deleteUser(Long id); // Soft delete

    // Búsqueda deportiva con filtros opcionales (UNIFICADO + UBICACIÓN)
    List<UsuarioResponseDTO> findUsuarios(NivelEnum nivel, Integer edadMin, Integer edadMax, Long ubicacionId);

    // TODO: Métodos pendientes de implementación para completar funcionalidades

    /**
     * Buscar usuarios por deporte específico
     * 
     * @param deporteId ID del deporte a filtrar
     * @param nivel     Nivel opcional en ese deporte
     * @return Lista de usuarios que practican el deporte
     */
    List<UsuarioResponseDTO> findUsuariosByDeporte(Long deporteId, NivelEnum nivel);

    /**
     * Buscar usuarios por historial de partidos
     * 
     * @param usuarioId ID del usuario para buscar compañeros de partidos anteriores
     * @return Lista de usuarios que han jugado con el usuario especificado
     */
    List<UsuarioResponseDTO> findUsuariosByHistorialPartidos(Long usuarioId);

    /**
     * Agregar un deporte a un usuario
     * 
     * @param usuarioId         ID del usuario
     * @param deporteId         ID del deporte
     * @param nivelEnDeporte    Nivel del usuario en ese deporte
     * @param posicionPreferida Posición preferida (opcional)
     */
    void agregarDeporteAUsuario(Long usuarioId, Long deporteId, NivelEnum nivelEnDeporte, String posicionPreferida);

    /**
     * Remover un deporte de un usuario (soft delete)
     * 
     * @param usuarioId ID del usuario
     * @param deporteId ID del deporte
     */
    void removerDeporteDeUsuario(Long usuarioId, Long deporteId);

    /**
     * Obtener deportes de un usuario
     * 
     * @param usuarioId ID del usuario
     * @return Lista de deportes que practica el usuario
     */
    List<UsuarioResponseDTO> getDeportesDeUsuario(Long usuarioId);
}
