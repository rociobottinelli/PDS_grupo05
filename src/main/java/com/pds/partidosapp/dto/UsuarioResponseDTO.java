package com.pds.partidosapp.dto;

import lombok.*;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.enums.NivelEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nombreUsuario;
    private String email;
    private Integer edad;
    private NivelEnum nivelJuego;
    private Boolean activo;

    public static UsuarioResponseDTO from(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .edad(usuario.getEdad())
                .nivelJuego(usuario.getNivelJuego())
                .activo(usuario.getActivo())
                .build();
    }
}