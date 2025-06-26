package com.pds.partidosapp.model.state;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;

public interface EstadoPartido {
    void aceptar(Partido partido, Usuario jugador);
    void cancelar(Partido partido);
    void iniciar(Partido partido);
    void finalizar(Partido partido);
    String nombreEstado();
}