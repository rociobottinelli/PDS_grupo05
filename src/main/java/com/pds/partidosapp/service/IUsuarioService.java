package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.UsuarioDTO;
import com.pds.partidosapp.model.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface IUsuarioService {
    UsuarioDTO getUsuario(int id);
    void createUser (UsuarioDTO usuarioDTO);
}
