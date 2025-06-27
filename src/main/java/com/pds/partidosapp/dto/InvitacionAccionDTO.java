package com.pds.partidosapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitacionAccionDTO {
    @NotNull(message = "El ID de la invitación es obligatorio")
    private Long invitacionId;
}
