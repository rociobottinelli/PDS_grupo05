package com.pds.partidosapp.model.state;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;

public class PartidoCancelado implements EstadoPartido {

    @Override
    public void aceptar(Partido partido, Usuario jugador) {
        throw new IllegalStateException("El partido fue cancelado. No se pueden agregar jugadores.");
    }

    @Override
    public void cancelar(Partido partido) {
        throw new IllegalStateException("El partido ya está cancelado.");
    }

    @Override
    public void iniciar(Partido partido) {
        throw new IllegalStateException("El partido fue cancelado. No se puede iniciar.");
    }

    @Override
    public void finalizar(Partido partido) {
        throw new IllegalStateException("El partido fue cancelado. No se puede finalizar.");
    }

    @Override
    public String nombreEstado() {
        return "PARTIDO_CANCELADO";
    }
}

