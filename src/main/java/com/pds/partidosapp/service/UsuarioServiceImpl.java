package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.RegisterDTO;
import com.pds.partidosapp.dto.UpdateUsuarioDTO;
import com.pds.partidosapp.dto.UsuarioResponseDTO;
import com.pds.partidosapp.enums.NivelEnum;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.model.entity.UsuarioDeporte;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.repository.DeporteRepository;
import com.pds.partidosapp.repository.UsuarioDeporteRepository;
import com.pds.partidosapp.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final DeporteRepository deporteRepository;
    private final UsuarioDeporteRepository usuarioDeporteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
        return UsuarioResponseDTO.from(usuario);
    }

    @Override
    public UsuarioResponseDTO createUser(RegisterDTO registerDTO) {
        // Validar email único
        if (usuarioRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + registerDTO.getEmail());
        }

        // Validar nombre de usuario único
        if (usuarioRepository.existsByNombreUsuario(registerDTO.getNombreUsuario())) {
            throw new IllegalArgumentException(
                    "El nombre de usuario ya está en uso: " + registerDTO.getNombreUsuario());
        }

        Usuario usuario = Usuario.builder()
                .nombreUsuario(registerDTO.getNombreUsuario())
                .email(registerDTO.getEmail())
                .contrasena(passwordEncoder.encode(registerDTO.getPassword()))
                .edad(registerDTO.getEdad())
                .nivelJuego(registerDTO.getNivelJuego())
                .activo(true)
                .build();

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.from(savedUsuario);
    }

    @Override
    public void updateUser(Long id, UpdateUsuarioDTO updateUsuarioDTO) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        // Validar email único si se está cambiando
        if (!usuario.getEmail().equals(updateUsuarioDTO.getEmail())) {
            if (usuarioRepository.existsByEmail(updateUsuarioDTO.getEmail())) {
                throw new IllegalArgumentException("El email ya está registrado: " + updateUsuarioDTO.getEmail());
            }
            usuario.setEmail(updateUsuarioDTO.getEmail());
        }

        // Validar nombre de usuario único si se está cambiando
        if (!usuario.getNombreUsuario().equals(updateUsuarioDTO.getNombreUsuario())) {
            if (usuarioRepository.existsByNombreUsuario(updateUsuarioDTO.getNombreUsuario())) {
                throw new IllegalArgumentException(
                        "El nombre de usuario ya está en uso: " + updateUsuarioDTO.getNombreUsuario());
            }
            usuario.setNombreUsuario(updateUsuarioDTO.getNombreUsuario());
        }

        // Actualizar campos básicos
        usuario.setEdad(updateUsuarioDTO.getEdad());
        usuario.setNivelJuego(updateUsuarioDTO.getNivelJuego());

        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUser(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        // Soft delete
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findUsuarios(NivelEnum nivel, Integer edadMin, Integer edadMax, Long ubicacionId) {
        List<Usuario> usuarios = usuarioRepository.findUsuariosActivos(nivel, edadMin, edadMax, ubicacionId);
        return usuarios.stream()
                .map(UsuarioResponseDTO::from)
                .collect(Collectors.toList());
    }

    // Método auxiliar para autenticación (usado por AuthService)
    @Transactional(readOnly = true)
    public Usuario findUserByEmailForAuth(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + email));
    }

    // TODO: Métodos pendientes de implementación para completar funcionalidades del
    // Trello

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findUsuariosByDeporte(Long deporteId, NivelEnum nivel) {
        // Verificar que el deporte exista
        if (!deporteRepository.existsById(deporteId)) {
            return List.of();
        }
        
        List<Usuario> usuarios;
        if (nivel != null) {
            // Buscar usuarios por deporte y nivel específico
            usuarios = usuarioDeporteRepository.findUsuariosByDeporteIdAndNivel(deporteId, nivel);
        } else {
            // Buscar todos los usuarios del deporte, sin filtrar por nivel
            usuarios = usuarioDeporteRepository.findUsuariosByDeporteId(deporteId);
        }
        
        // Convertir a DTOs y devolver
        return usuarios.stream()
                .filter(Usuario::getActivo) // Solo usuarios activos
                .map(UsuarioResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findUsuariosByHistorialPartidos(Long usuarioId) {
        // TODO: Implementar búsqueda de usuarios por historial de partidos
        // Necesita: PartidoRepository, UsuarioPartidoRepository (relación Many-to-Many)
        throw new UnsupportedOperationException("Método pendiente de implementación: findUsuariosByHistorialPartidos");
    }

    @Override
    public void agregarDeporteAUsuario(Long usuarioId, Long deporteId, NivelEnum nivelEnDeporte) {
        // Validar que el usuario existe y está activo
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado o inactivo con ID: " + usuarioId));
        
        // Validar que el deporte existe
        if (!deporteRepository.existsById(deporteId)) {
            throw new NotFoundException("Deporte no encontrado con ID: " + deporteId);
        }
        
        // Verificar si ya existe una relación activa entre el usuario y el deporte
        boolean yaExiste = usuarioDeporteRepository.existsByUsuarioIdAndDeporteIdAndActivoTrue(usuarioId, deporteId);
        if (yaExiste) {
            throw new IllegalArgumentException("El usuario ya tiene asignado este deporte");
        }
        
        // Crear la relación UsuarioDeporte
        UsuarioDeporte usuarioDeporte = UsuarioDeporte.builder()
                .usuario(usuario)
                .deporteId(deporteId)
                .nivelEnDeporte(nivelEnDeporte)
                .activo(true)
                .build();
        
        // Guardar la relación
        usuarioDeporteRepository.save(usuarioDeporte);
    }

    @Override
    public void removerDeporteDeUsuario(Long usuarioId, Long deporteId) {
        // TODO: Implementar remover deporte de usuario (soft delete)
        // Necesita: UsuarioDeporteRepository
        // Buscar la relación y marcar activo = false
        throw new UnsupportedOperationException("Método pendiente de implementación: removerDeporteDeUsuario");
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> getDeportesDeUsuario(Long usuarioId) {
        // TODO: Implementar obtener deportes de un usuario
        // Necesita: UsuarioDeporteRepository
        // Retornar lista de deportes que practica el usuario (activos)
        throw new UnsupportedOperationException("Método pendiente de implementación: getDeportesDeUsuario");
    }
}
