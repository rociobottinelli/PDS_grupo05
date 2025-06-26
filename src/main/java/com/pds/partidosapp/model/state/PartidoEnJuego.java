package com.pds.partidosapp.model.state;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;

public class PartidoEnJuego implements EstadoPartido {

    @Override
    public void aceptar(Partido partido, Usuario jugador) {
        throw new IllegalStateException("No se pueden sumar jugadores durante el partido.");
    }

    @Override
    public void cancelar(Partido partido) {
        throw new IllegalStateException("No se puede cancelar un partido que está en juego.");
    }

    @Override
    public void iniciar(Partido partido) {
        throw new IllegalStateException("El partido ya está en juego.");
    }

    @Override
    public void finalizar(Partido partido) {
        partido.setEstadoActual(new PartidoFinalizado());
    }

    @Override
    public String nombreEstado() {
        return "PARTIDO_EN_JUEGO";
    }
}

