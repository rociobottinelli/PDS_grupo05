package com.pds.partidosapp.model;

import com.pds.partidosapp.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una notificación en el sistema.
 * Contiene la información del usuario destinatario y el mensaje de la notificación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    
    private UsuarioDTO usuario;
    private String mensaje;
}
