package com.pds.partidosapp.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ubicaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    // TODO: Relaciones cuando estén implementadas
    // List<Partido> partidos -> One-to-Many
}