package com.pds.partidosapp.controller;

import com.pds.partidosapp.config.JwtTokenProvider;
import com.pds.partidosapp.model.entity.Invitacion;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.InvitacionRepository;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.service.IInvitacionService;
import com.pds.partidosapp.service.PartidoService;
import com.pds.partidosapp.shared.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/invitaciones")
@RequiredArgsConstructor
public class InvitacionController {

    private final IInvitacionService invitacionService;
    private final PartidoService partidoService;
    private final InvitacionRepository invitacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthUtils authUtils;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/aceptar")
    public ResponseEntity<?> aceptarInvitacion(@Valid @RequestParam Long invitacionId) {
        Long usuarioId = authUtils.getCurrentUserId();

        // Obtener la invitación por su ID
        Invitacion invitacion = invitacionRepository.findById(invitacionId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la invitación con ID: " + invitacionId));
        
        // Verificar que el usuario de la invitación coincide con el usuario autenticado
        if (!invitacion.getIdUsuario().equals(usuarioId)) {
            return ResponseEntity.badRequest().body("No tienes permiso para aceptar esta invitación");
        }
        
        // Aceptar la invitación
        invitacionService.aceptar(invitacion.getId());
        
        // Agregar el jugador al partido
        partidoService.aceptarPartido(invitacion.getIdPartido(), usuarioId);
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/rechazar")
    public ResponseEntity<?> rechazarInvitacion(@Valid @RequestParam Long invitacionId) {
        
        Long usuarioId = authUtils.getCurrentUserId();

        Invitacion invitacion = invitacionRepository.findById(invitacionId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la invitación con ID: " + invitacionId));
        
        // Verificar que el usuario de la invitación coincide con el usuario autenticado
        if (!invitacion.getIdUsuario().equals(usuarioId)) {
            return ResponseEntity.badRequest().body("No tienes permiso para rechazar esta invitación");
        }
        
        // Rechazar la invitación
        invitacionService.cancelar(invitacion.getId());
        
        return ResponseEntity.ok().build();
    }

}
