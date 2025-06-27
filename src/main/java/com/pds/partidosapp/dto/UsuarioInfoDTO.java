package com.pds.partidosapp.dto;

import lombok.*;
import com.pds.partidosapp.model.entity.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioInfoDTO {
  private Long id;
  private String nombreUsuario;

  public static UsuarioInfoDTO from(Usuario usuario) {
    if (usuario == null)
      return null;

    return UsuarioInfoDTO.builder()
        .id(usuario.getId())
        .nombreUsuario(usuario.getNombreUsuario())
        .build();
  }
}