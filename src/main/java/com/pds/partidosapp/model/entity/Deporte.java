package com.pds.partidosapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Entity
@Table(name = "deportes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nombre;
    @NotBlank
    @Column(nullable = false)
    private String descripcion;
    @Min(1)
    @Column(nullable = false)
    private int cantidadJugadores;
}