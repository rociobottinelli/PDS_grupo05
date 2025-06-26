package com.pds.partidosapp.model.strategy;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.model.entity.Ubicacion;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class EmparejamientoCercania implements Emparejamiento {

    @Override
    public List<Usuario> sugerirJugadores(Partido partido, List<Usuario> candidatos) {
        if (partido.getUbicacion() == null) return candidatos;

        return candidatos.stream()
                .filter(c -> !partido.getJugadores().contains(c)) // Evitar proponer jugadores ya en el partido
                .filter(c -> c.getUbicacion() != null)
                .sorted(Comparator.comparingDouble(c -> distancia(partido.getUbicacion(), c.getUbicacion())))
                .toList();
    }

    private double distancia(Ubicacion u1, Ubicacion u2) {
        double dx = u1.getLatitud() - u2.getLatitud();
        double dy = u1.getLongitud() - u2.getLongitud();
        return Math.sqrt(dx * dx + dy * dy);  // Distancia Euclidiana simple (aproximada)
    }
}
