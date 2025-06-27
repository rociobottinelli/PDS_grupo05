package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.AddDeporteToUsuarioDTO;
import com.pds.partidosapp.dto.UpdateUsuarioDTO;
import com.pds.partidosapp.dto.UsuarioResponseDTO;
import com.pds.partidosapp.enums.NivelEnum;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.service.IUsuarioService;
import com.pds.partidosapp.shared.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;
    private final AuthUtils authUtils;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(
            @RequestParam(required = false) NivelEnum nivel,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax,
            @RequestParam(required = false) Long ubicacionId) {

        List<UsuarioResponseDTO> usuarios = usuarioService.findUsuarios(nivel, edadMin, edadMax, ubicacionId);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.getUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateUsuarioDTO dto) {
        if (!authUtils.canModifyResource(id)) {
            throw new AccessDeniedException("No puedes modificar el perfil de otro usuario");
        }

        usuarioService.updateUser(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!authUtils.canModifyResource(id)) {
            throw new AccessDeniedException("No puedes eliminar el perfil de otro usuario");
        }

        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/me/deportes")
    public ResponseEntity<Void> agregarDeporteAlUsuarioActual(
            @RequestBody @Valid AddDeporteToUsuarioDTO dto) {

        Usuario usuario = authUtils.getCurrentUser();

        usuarioService.agregarDeporteAUsuario(
                usuario.getId(),
                dto.getDeporteId(),
                dto.getNivelEnDeporte());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
