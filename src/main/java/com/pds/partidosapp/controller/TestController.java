package com.pds.partidosapp.controller;

import com.pds.partidosapp.dto.UsuarioDTO;
import com.pds.partidosapp.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/test")
@Validated
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private final IUsuarioService userService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> createUser (@RequestBody UsuarioDTO usuarioDTO) {
        try {
            userService.createUser(usuarioDTO);
            return ResponseEntity.created(new URI("")).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/usuarioId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> getUserData (@PathVariable int usuarioId) {
        return ResponseEntity.ok(userService.getUsuario(usuarioId));
    }


    @GetMapping("/hola")
    public ResponseEntity<String> hola() {
        return new ResponseEntity<>("Hola!", HttpStatus.OK);
    }
}
