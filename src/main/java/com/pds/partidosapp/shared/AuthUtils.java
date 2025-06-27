package com.pds.partidosapp.shared;

import com.pds.partidosapp.config.JwtTokenProvider;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utilidad centralizada para manejo de autenticación y extracción de usuario
 * actual
 * Evita duplicación de código entre controllers
 */
@Component
@RequiredArgsConstructor
public class AuthUtils {

  private final JwtTokenProvider jwtTokenProvider;
  private final UsuarioRepository usuarioRepository;

  /**
   * Obtiene el email del usuario autenticado usando SecurityContext
   * 
   * @return Email del usuario actual
   * @throws NotFoundException Si no hay usuario autenticado
   */
  public String getCurrentUserEmail() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() ||
        authentication.getPrincipal().equals("anonymousUser")) {
      throw new NotFoundException("Usuario no autenticado");
    }

    return (String) authentication.getPrincipal();
  }

  /**
   * Obtiene el usuario actual completo usando SecurityContext
   * 
   * @return Usuario autenticado actual
   * @throws NotFoundException Si no hay usuario autenticado o no existe en BD
   */
  public Usuario getCurrentUser() {
    String email = getCurrentUserEmail();
    return usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + email));
  }

  /**
   * Obtiene el ID del usuario actual
   * 
   * @return ID del usuario autenticado
   */
  public Long getCurrentUserId() {
    return getCurrentUser().getId();
  }

  /**
   * Método de compatibilidad: extrae usuario de header Authorization
   * 
   * @deprecated Usar getCurrentUser() en su lugar
   * @param authHeader Header Authorization con Bearer token
   * @return ID del usuario
   */
  @Deprecated
  public Long extractUserIdFromToken(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Header Authorization inválido");
    }

    String token = authHeader.substring(7);
    String email = jwtTokenProvider.getEmail(token);

    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + email));

    return usuario.getId();
  }

  /**
   * Verifica si el usuario actual puede modificar el recurso especificado
   * 
   * @param resourceOwnerId ID del propietario del recurso
   * @return true si el usuario actual puede modificar el recurso
   */
  public boolean canModifyResource(Long resourceOwnerId) {
    Long currentUserId = getCurrentUserId();
    return currentUserId.equals(resourceOwnerId);
  }
}