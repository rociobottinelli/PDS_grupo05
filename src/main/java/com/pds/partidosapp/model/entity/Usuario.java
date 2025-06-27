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

    @Builder.Default
    private Integer cantidadPartidosJugados = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToMany
    @JoinTable(name = "usuario_deportes", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "deporte_id"))
    private List<Deporte> deportes;
}
