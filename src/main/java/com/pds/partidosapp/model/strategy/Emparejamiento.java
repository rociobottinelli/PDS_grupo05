package com.pds.partidosapp.model.strategy;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;

import java.util.List;

public interface Emparejamiento {
    List<Usuario> sugerirJugadores(Partido partido, List<Usuario> candidatos);
}

