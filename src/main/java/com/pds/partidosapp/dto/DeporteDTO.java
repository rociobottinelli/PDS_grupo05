package com.pds.partidosapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeporteDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;
    @Min(value = 1, message = "Debe haber al menos un jugador")
    private int cantidadJugadores;

    public DeporteDTO(String nombre, String descripcion, int cantidadJugadores) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadJugadores = cantidadJugadores;
    }
}
