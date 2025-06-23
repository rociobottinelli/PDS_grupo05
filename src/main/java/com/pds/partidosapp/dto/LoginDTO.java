package com.pds.partidosapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

  @Email(message = "El email debe tener un formato válido")
  @NotBlank(message = "El email es obligatorio")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}