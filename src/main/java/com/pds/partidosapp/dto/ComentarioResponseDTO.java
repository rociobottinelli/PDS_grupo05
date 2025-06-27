package com.pds.partidosapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ComentarioResponseDTO {
    private String comentario;
    private Double resenia;
    private String usuarioOrigen;
}