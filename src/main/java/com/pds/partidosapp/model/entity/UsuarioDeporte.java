package com.pds.partidosapp.model.entity;

import com.pds.partidosapp.enums.NivelEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_deportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDeporte {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  // TODO: Descomentar cuando se implemente la entidad Deporte
  // @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "deporte_id", nullable = false)
  // private Deporte deporte;

  // Temporal: ID del deporte hasta que se implemente la entidad
  @Column(name = "deporte_id", nullable = false)
  private Long deporteId;

  // Nivel específico del usuario en este deporte (puede ser diferente al nivel
  // general)
  @Enumerated(EnumType.STRING)
  private NivelEnum nivelEnDeporte;

  @Builder.Default
  private Boolean activo = true;

  // Campos adicionales específicos del deporte
  private String posicionPreferida; // "Arquero", "Delantero", etc.
  private Integer experienciaAnios;
}