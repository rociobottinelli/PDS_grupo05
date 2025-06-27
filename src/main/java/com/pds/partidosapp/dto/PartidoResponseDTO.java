package com.pds.partidosapp.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartidoResponseDTO {
    private Long id;
    private UsuarioInfoDTO organizador;
    private String estado;
    private LocalDateTime fechaHora;
    private UbicacionDTO ubicacion;
    private DeporteInfoDTO deporte;
    private Integer jugadoresRequeridos;
    private List<UsuarioInfoDTO> jugadores;
} 