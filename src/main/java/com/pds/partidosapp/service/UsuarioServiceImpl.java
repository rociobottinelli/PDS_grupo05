package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.UsuarioDTO;
import com.pds.partidosapp.mapper.UsuarioMapper;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO getUsuario(int id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return UsuarioMapper.toDTO(usuario);
    }

    @Override
    public void createUser (UsuarioDTO usuarioDTO) {
        usuarioRepository.save(UsuarioMapper.toEntity(usuarioDTO));
    }

}
