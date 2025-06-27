package com.pds.partidosapp.dto;

import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitacionResponseDTO {
    private Long id;
    private Long partidoId;
    private Long usuarioId;
    private EstadoInvitacionEnum estadoInvitacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
