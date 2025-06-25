package com.pds.partidosapp.model.entity;

import com.pds.partidosapp.enums.EstadoInvitacionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una invitación a un partido en el sistema.
 * Está mapeada a la tabla 'invitaciones' en la base de datos.
 */
@Entity
@Table(name = "invitaciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitacion {

    /**
     * Identificador único de la invitación.
     * Se genera automáticamente como una secuencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID del partido al que se refiere la invitación.
     * Se mapea a la columna 'partido_id' en la base de datos.
     */
    @Column(name = "partido_id", nullable = false)
    private Long idPartido;

    /**
     * ID del usuario invitado.
     * Se mapea a la columna 'usuario_id' en la base de datos.
     */
    @Column(name = "usuario_id", nullable = false)
    private Long idUsuario;

    /**
     * Estado actual de la invitación.
     * Se mapea como un enumerado de tipo String en la base de datos.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInvitacionEnum estadoInvitacion;
}