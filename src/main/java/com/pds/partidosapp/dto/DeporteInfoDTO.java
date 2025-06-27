package com.pds.partidosapp.dto;

import lombok.*;
import com.pds.partidosapp.model.entity.Deporte;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeporteInfoDTO {
  private Long id;
  private String nombre;
  private String descripcion;
  private Integer cantidadJugadores;

  public static DeporteInfoDTO from(Deporte deporte) {
    if (deporte == null)
      return null;

    return DeporteInfoDTO.builder()
        .id(deporte.getId())
        .nombre(deporte.getNombre())
        .descripcion(deporte.getDescripcion())
        .cantidadJugadores(deporte.getCantidadJugadores())
        .build();
  }
}