package com.pds.partidosapp.mapper;

import com.pds.partidosapp.dto.UsuarioDTO;
import com.pds.partidosapp.model.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getNombreUsuario(),
                usuario.getContrasena(),
                usuario.getMail());
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setMail(usuarioDTO.getMail());
        return  usuario;
    }
}
