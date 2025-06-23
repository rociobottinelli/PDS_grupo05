package com.pds.partidosapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.pds.partidosapp.enums.NivelEnum;

@Data
public class UpdateUsuarioDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "El email es obligatorio")  
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 13, message = "La edad mínima es 13 años")
    @Max(value = 100, message = "La edad máxima es 100 años")
    private Integer edad;

    @NotNull(message = "El nivel de juego es obligatorio")
    private NivelEnum nivelJuego;

    // Ubicación opcional
    private Long ubicacionId;
} 