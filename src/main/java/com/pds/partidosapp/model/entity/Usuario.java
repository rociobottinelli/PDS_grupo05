package com.pds.partidosapp.model.entity;

import com.pds.partidosapp.enums.NivelEnum;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombreUsuario;

    @Column(unique = true)
    private String email;

    private String contrasena;

    private Integer edad;

    @Enumerated(EnumType.STRING)
    private NivelEnum nivelJuego;

    @Builder.Default
    private Boolean activo = true;

    // Relación con Ubicacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToMany(mappedBy = "jugadores")
    private List<Partido> partidosJugados = new ArrayList<>();


    // TODO: Conectar con otras entidades cuando estén implementadas
    // List<UsuarioDeporte> deportes -> relación Many-to-Many
    // List<Partido> partidos -> para historial
    // List<Equipo> equipos -> Many-to-Many
    // List<Invitacion> invitaciones -> One-to-Many
}
