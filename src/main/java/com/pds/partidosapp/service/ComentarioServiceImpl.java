package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.ComentarioDTO;
import com.pds.partidosapp.dto.ComentarioResponseDTO;
import com.pds.partidosapp.model.entity.Comentario;
import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.ComentarioRepository;
import com.pds.partidosapp.repository.PartidoRepository;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements IComentarioService {

    private final ComentarioRepository comentarioRepo;
    private final PartidoRepository partidoRepo;
    private final UsuarioRepository usuarioRepo;

    @Override
    public void guardarComentario(Long partidoId, ComentarioDTO dto) {
        Partido partido = partidoRepo.findById(partidoId)
                .orElseThrow(() -> new NotFoundException("Partido no encontrado"));

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Comentario comentario = Comentario.builder()
                .comentario(dto.getComentario())
                .resenia(dto.getResenia())
                .usuario(usuario)
                .partido(partido)
                .build();

        comentarioRepo.save(comentario);
    }

    @Override
    public List<ComentarioResponseDTO> listarComentariosDePartido(Long partidoId) {
        Partido partido = partidoRepo.findById(partidoId)
                .orElseThrow(() -> new NotFoundException("Partido no encontrado"));

        return comentarioRepo.findByPartido(partido).stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getComentario(),
                        c.getResenia(),
                        c.getUsuario().getNombreUsuario()))
                .collect(Collectors.toList());
    }
}
