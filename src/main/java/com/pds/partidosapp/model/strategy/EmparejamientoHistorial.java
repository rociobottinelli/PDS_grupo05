package com.pds.partidosapp.model.strategy;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class EmparejamientoHistorial implements Emparejamiento {

    @Override
    public List<Usuario> sugerirJugadores(Partido partido, List<Usuario> candidatos) {
        int partidosOrganizador = partido.getOrganizador().getPartidosJugados() != null
                ? partido.getOrganizador().getPartidosJugados().size()
                : 0;

        return candidatos.stream()
                .filter(c -> !partido.getJugadores().contains(c)) // Evitar proponer jugadores ya en el partido
                .sorted(Comparator.comparingInt(c -> Math.abs(
                        (c.getPartidosJugados() != null ? c.getPartidosJugados().size() : 0) - partidosOrganizador)))
                .toList();
    }
}


