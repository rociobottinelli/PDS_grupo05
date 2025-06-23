package com.pds.partidosapp.service;

import com.pds.partidosapp.config.JwtTokenProvider;
import com.pds.partidosapp.dto.LoginDTO;
import com.pds.partidosapp.dto.RegisterDTO;
import com.pds.partidosapp.dto.UsuarioResponseDTO;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public String login(LoginDTO loginDTO) {
    // 1. Buscar usuario por email (incluye inactivos para login)
    Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
        .orElseThrow(() -> new NotFoundException("Email o contraseña incorrectos"));

    // 2. Verificar que el usuario esté activo
    if (!usuario.getActivo()) {
      throw new NotFoundException("Usuario inactivo");
    }

    // 3. Verificar contraseña
    if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getContrasena())) {
      throw new NotFoundException("Email o contraseña incorrectos");
    }

    // 4. Generar y retornar token JWT
    return jwtTokenProvider.generateToken(usuario);
  }



  
  public String register(RegisterDTO registerDTO) {
    // 1. Verificar que email no exista
    if (usuarioRepository.existsByEmail(registerDTO.getEmail())) {
      throw new IllegalArgumentException("El email ya está registrado");
    }

    if (usuarioRepository.existsByNombreUsuario(registerDTO.getNombreUsuario())) {
      throw new IllegalArgumentException("El nombre de usuario ya está en uso");
    }

    // 2. Crear usuario
    Usuario usuario = Usuario.builder()
        .nombreUsuario(registerDTO.getNombreUsuario())
        .email(registerDTO.getEmail())
        .contrasena(passwordEncoder.encode(registerDTO.getPassword()))
        .edad(registerDTO.getEdad())
        .nivelJuego(registerDTO.getNivelJuego())
        .activo(true)
        .build();

    Usuario savedUsuario = usuarioRepository.save(usuario);

    // 3. Generar token automáticamente
    return jwtTokenProvider.generateToken(savedUsuario);
  }


  public UsuarioResponseDTO getProfile(String email) {
    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

    return UsuarioResponseDTO.from(usuario);
  }

}