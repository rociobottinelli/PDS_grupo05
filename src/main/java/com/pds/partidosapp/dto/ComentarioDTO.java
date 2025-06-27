package com.pds.partidosapp.dto;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComentarioDTO {

    @NotBlank(message = "El comentario no puede estar vacío")
    private String comentario;

    @NotNull(message = "La reseña es obligatoria")
    @Min(value = 0, message = "La reseña mínima es 0")
    @Max(value = 5, message = "La reseña máxima es 5")
    private Double resenia;
}