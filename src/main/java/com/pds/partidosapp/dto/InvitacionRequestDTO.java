package com.pds.partidosapp.dto;

import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitacionRequestDTO {
    @NotNull(message = "El ID del partido es obligatorio")
    private Long partidoId;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
    
    private EstadoInvitacionEnum estadoInvitacion;
}
