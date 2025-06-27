package com.pds.partidosapp.dto;

import com.pds.partidosapp.enums.NivelEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDeporteToUsuarioDTO {
    @NotNull(message = "El ID del deporte es obligatorio")
    private Long deporteId;
    
    @NotNull(message = "El nivel en el deporte es obligatorio")
    private NivelEnum nivelEnDeporte;
}
