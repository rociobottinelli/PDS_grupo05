package com.pds.partidosapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UbicacionDTO {

  @NotNull(message = "La latitud es obligatoria")
  @DecimalMin(value = "-90.0", message = "La latitud debe estar entre -90 y 90 grados")
  @DecimalMax(value = "90.0", message = "La latitud debe estar entre -90 y 90 grados")
  private Double latitud;

  @NotNull(message = "La longitud es obligatoria")
  @DecimalMin(value = "-180.0", message = "La longitud debe estar entre -180 y 180 grados")
  @DecimalMax(value = "180.0", message = "La longitud debe estar entre -180 y 180 grados")
  private Double longitud;
}
