package com.pds.partidosapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.pds.partidosapp.model.state.EstadoPartido;

@Entity
@Table(name = "partidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el organizador del partido
    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    // Lista de jugadores participantes
    @ManyToMany
    @JoinTable(
        name = "partido_jugadores",
        joinColumns = @JoinColumn(name = "partido_id"),
        inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    @Builder.Default
    private List<Usuario> jugadores = new ArrayList<>();

    // Estado actual del partido (se guarda como String por ahora)
    @Column(nullable = false)
    private String estado;

    // Fecha y hora del partido
    private LocalDateTime fechaHora;

    // Ubicación del partido
    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    // Deporte del partido
    @ManyToOne
    @JoinColumn(name = "deporte_id")
    private Deporte deporte;

    // Cantidad de jugadores requeridos
    private Integer jugadoresRequeridos;

    @Transient
    private EstadoPartido estadoActual;

    public void setEstadoActual(EstadoPartido nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estado = nuevoEstado.nombreEstado();
    }

}