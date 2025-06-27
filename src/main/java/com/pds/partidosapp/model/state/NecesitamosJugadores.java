package com.pds.partidosapp.model.state;

import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NecesitamosJugadores implements EstadoPartido {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void aceptar(Partido partido, Usuario jugador) {
        // Verificar si el jugador ya está inscrito en el partido
        boolean yaEstaInscrito = partido.getJugadores().stream()
                .anyMatch(j -> j.getId().equals(jugador.getId()));

        if (yaEstaInscrito) {
            throw new IllegalStateException("El jugador ya está inscrito en este partido");
        }

        partido.getJugadores().add(jugador);

        // Incrementar contador de partidos jugados
        jugador.setCantidadPartidosJugados(jugador.getCantidadPartidosJugados() + 1);
        usuarioRepository.save(jugador); // Persistir el cambio

        if (partido.getJugadores().size() >= partido.getJugadoresRequeridos()) {
            partido.setEstadoActual(new PartidoArmado());
            partido.setEstado("PARTIDO_ARMADO");
        }
    }

    @Override
    public void cancelar(Partido partido) {
        partido.setEstadoActual(new PartidoCancelado());
        partido.setEstado("PARTIDO_CANCELADO");
    }

    @Override
    public void iniciar(Partido partido) {
        throw new IllegalStateException("No se puede iniciar un partido que aún no está armado.");
    }

    @Override
    public void finalizar(Partido partido) {
        throw new IllegalStateException("No se puede finalizar un partido que aún no empezó.");
    }

    @Override
    public String nombreEstado() {
        return "NECESITAMOS_JUGADORES";
    }
}
