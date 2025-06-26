package com.pds.partidosapp.model.strategy;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.enums.NivelEnum;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class EmparejamientoNivel implements Emparejamiento {

    @Override
    public List<Usuario> sugerirJugadores(Partido partido, List<Usuario> candidatos) {
        // Ordenar por cercanía de nivel al promedio o al organizador
        NivelEnum nivelReferencia = partido.getOrganizador().getNivelJuego();

        return candidatos.stream()
                .filter(c -> !partido.getJugadores().contains(c)) // Evitar proponer jugadores ya en el partido
                .filter(c -> c.getNivelJuego() != null)
                .sorted(Comparator.comparingInt(c -> Math.abs(c.getNivelJuego().ordinal() - nivelReferencia.ordinal())))
                .toList();
    }
}
