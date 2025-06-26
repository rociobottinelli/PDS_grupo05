package com.pds.partidosapp.model.state;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;

public class PartidoFinalizado implements EstadoPartido {

    @Override
    public void aceptar(Partido partido, Usuario jugador) {
        throw new IllegalStateException("El partido ya finalizó. No se pueden agregar jugadores.");
    }

    @Override
    public void cancelar(Partido partido) {
        throw new IllegalStateException("El partido ya finalizó. No se puede cancelar.");
    }

    @Override
    public void iniciar(Partido partido) {
        throw new IllegalStateException("El partido ya finalizó. No se puede iniciar.");
    }

    @Override
    public void finalizar(Partido partido) {
        throw new IllegalStateException("El partido ya está finalizado.");
    }

    @Override
    public String nombreEstado() {
        return "PARTIDO_FINALIZADO";
    }
}

