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
                // Usar el campo simple de cantidad de partidos
                int partidosOrganizador = partido.getOrganizador().getCantidadPartidosJugados();

                return candidatos.stream()
                                .filter(c -> !partido.getJugadores().contains(c)) // Evitar proponer jugadores ya en el
                                                                                  // partido
                                .filter(c -> c.getActivo()) // Solo usuarios activos
                                .sorted(Comparator.comparingInt(c -> Math.abs(
                                                c.getCantidadPartidosJugados() - partidosOrganizador)))
                                .toList();
        }
}
