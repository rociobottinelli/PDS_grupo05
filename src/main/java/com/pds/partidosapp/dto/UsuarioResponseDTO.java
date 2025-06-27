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
    private Integer cantidadPartidosJugados;
    private UbicacionDTO ubicacion;

    public static UsuarioResponseDTO from(Usuario usuario) {
        UbicacionDTO ubicacionDTO = null;
        if (usuario.getUbicacion() != null) {
            ubicacionDTO = UbicacionDTO.builder()
                    .latitud(usuario.getUbicacion().getLatitud())
                    .longitud(usuario.getUbicacion().getLongitud())
                    .build();
        }

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .edad(usuario.getEdad())
                .nivelJuego(usuario.getNivelJuego())
                .activo(usuario.getActivo())
                .cantidadPartidosJugados(
                        usuario.getCantidadPartidosJugados() != null ? usuario.getCantidadPartidosJugados() : 0)
                .ubicacion(ubicacionDTO)
                .build();
    }
}