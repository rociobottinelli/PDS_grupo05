package com.pds.partidosapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pds.partidosapp.model.state.EstadoPartido;
import com.pds.partidosapp.observer.Observable;
import com.pds.partidosapp.observer.Observer;

@Entity
@Table(name = "partidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido implements Observable {

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

    @Transient
    private final List<Observer> observadores = new ArrayList<>();

    @Override
    public void attach(Observer observador) {
        observadores.add(observador);
    }

    @Override
    public void detach(Observer observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificar() {
        if (observadores != null) {
            // Create a copy to avoid ConcurrentModificationException
            for (Observer observador : new ArrayList<>(observadores)) {
                try {
                    observador.update(this);
                } catch (Exception e) {
                    // Log the error but don't fail the operation
                    e.printStackTrace();
                }
            }
        }
    }

    public void setEstadoActual(EstadoPartido nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estado = nuevoEstado.nombreEstado();
        
        // Notificar a los observadores sobre el cambio de estado
        notificar();
    }

}