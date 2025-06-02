package com.pds.partidosapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private String nombreUsuario;
    private String contrasena;
    private String mail;

    public UsuarioDTO(String nombreUsuario, String contrasena, String mail) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.mail = mail;
    }

}
