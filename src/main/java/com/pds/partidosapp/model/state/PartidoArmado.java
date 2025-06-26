package com.pds.partidosapp.model.state;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;

public class PartidoArmado implements EstadoPartido {

    @Override
    public void aceptar(Partido partido, Usuario jugador) {
        throw new IllegalStateException("El partido ya está armado. No se pueden sumar más jugadores.");
    }

    @Override
    public void cancelar(Partido partido) {
        partido.setEstadoActual(new PartidoCancelado());
    }

    @Override
    public void iniciar(Partido partido) {
        partido.setEstadoActual(new PartidoEnJuego());
    }

    @Override
    public void finalizar(Partido partido) {
        throw new IllegalStateException("No se puede finalizar un partido que aún no empezó.");
    }

    @Override
    public String nombreEstado() {
        return "PARTIDO_ARMADO";
    }
}
