package com.pds.partidosapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidoDTO {

    private Long id;

    @NotNull(message = "El ID del organizador es obligatorio")
    private Long organizadorId;

    @NotNull(message = "El estado del partido es obligatorio")
    private String estado;

    @Future(message = "La fecha y hora del partido debe ser futura")
    private LocalDateTime fechaHora;

    private Long ubicacionId;

    private Long deporteId;

    private Integer jugadoresRequeridos;

    private List<Long> jugadoresIds;
}
