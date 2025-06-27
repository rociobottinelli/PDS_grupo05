package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.PartidoDTO;
import com.pds.partidosapp.dto.PartidoResponseDTO;
import com.pds.partidosapp.dto.UsuarioInfoDTO;
import com.pds.partidosapp.dto.DeporteInfoDTO;
import com.pds.partidosapp.dto.UbicacionDTO;
import com.pds.partidosapp.model.entity.Deporte;
import com.pds.partidosapp.model.entity.Partido;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.model.state.EstadoPartido;
import com.pds.partidosapp.model.state.NecesitamosJugadores;
import com.pds.partidosapp.model.state.PartidoArmado;
import com.pds.partidosapp.model.state.PartidoConfirmado;
import com.pds.partidosapp.model.state.PartidoEnJuego;
import com.pds.partidosapp.model.state.PartidoFinalizado;
import com.pds.partidosapp.model.state.PartidoCancelado;
import com.pds.partidosapp.model.strategy.EmparejamientoNivel;
import com.pds.partidosapp.model.strategy.EmparejamientoCercania;
import com.pds.partidosapp.model.strategy.EmparejamientoHistorial;
import com.pds.partidosapp.repository.PartidoRepository;
import com.pds.partidosapp.repository.UsuarioRepository;
import com.pds.partidosapp.repository.DeporteRepository;
import com.pds.partidosapp.service.PartidoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DeporteRepository deporteRepository;

    private final EmparejamientoNivel emparejamientoNivel;
    private final EmparejamientoCercania emparejamientoCercania;
    private final EmparejamientoHistorial emparejamientoHistorial;

    @Autowired
    private NotificadorService notificadorService;

    @Override
    public PartidoDTO crearPartido(PartidoDTO partidoDTO) {
        Usuario organizador = usuarioRepository.findById(partidoDTO.getOrganizadorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Organizador no encontrado con ID: " + partidoDTO.getOrganizadorId()));

        Deporte deporte = deporteRepository.findById(partidoDTO.getDeporteId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Deporte no encontrado con ID: " + partidoDTO.getDeporteId()));

        // Validar conflictos de horario
        validarConflictoDeHorario(organizador, partidoDTO.getFechaHora());

        Partido partido = Partido.builder()
                .organizador(organizador)
                .estado(partidoDTO.getEstado())
                .fechaHora(partidoDTO.getFechaHora())
                .jugadoresRequeridos(partidoDTO.getJugadoresRequeridos())
                .jugadores(new ArrayList<>())
                .deporte(deporte)
                .ubicacion(organizador.getUbicacion())
                .build();

        partido.getJugadores().add(organizador);
        partido.setEstadoActual(new NecesitamosJugadores());

        Partido partidoGuardado = partidoRepository.save(partido);
        partidoGuardado.attach(notificadorService);
        partidoGuardado.notificar();

        return mapToDTO(partidoGuardado);
    }

    @Override
    public PartidoDTO getPartidoById(Long id) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + id));

        return mapToDTO(partido);
    }

    @Override
    public PartidoDTO aceptarPartido(Long partidoId, Long idUsuarioActual) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + partidoId));

        Usuario jugador = usuarioRepository.findById(idUsuarioActual)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuarioActual));
        partido.attach(notificadorService);

        partido.setEstadoActual(getEstadoPartidoDesdeString(partido.getEstado()));

        partido.getEstadoActual().aceptar(partido, jugador);

        Partido partidoActualizado = partidoRepository.save(partido);

        return mapToDTO(partidoActualizado);
    }

    @Override
    public List<Usuario> sugerirJugadores(Long partidoId, String criterio) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado"));

        List<Usuario> candidatos = usuarioRepository.findAll();

        return switch (criterio.toLowerCase()) {
            case "nivel" -> emparejamientoNivel.sugerirJugadores(partido, candidatos);
            case "cercania" -> emparejamientoCercania.sugerirJugadores(partido, candidatos);
            case "historial" -> emparejamientoHistorial.sugerirJugadores(partido, candidatos);
            default -> throw new IllegalArgumentException("Criterio de emparejamiento desconocido: " + criterio);
        };
    }

    @Override
    public PartidoDTO cancelarPartido(Long partidoId, Long idUsuarioActual) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + partidoId));

        Usuario usuario = usuarioRepository.findById(idUsuarioActual)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuarioActual));

        if (!partido.getOrganizador().getId().equals(usuario.getId())) {
            throw new IllegalStateException("Solo el organizador puede cancelar el partido.");
        }
        partido.attach(notificadorService);

        partido.setEstadoActual(getEstadoPartidoDesdeString(partido.getEstado()));

        partido.getEstadoActual().cancelar(partido);

        Partido partidoActualizado = partidoRepository.save(partido);

        return mapToDTO(partidoActualizado);
    }

    @Override
    public PartidoDTO iniciarPartido(Long partidoId, Long idUsuarioActual) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + partidoId));

        Usuario usuario = usuarioRepository.findById(idUsuarioActual)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuarioActual));

        if (!partido.getOrganizador().getId().equals(usuario.getId())) {
            throw new IllegalStateException("Solo el organizador puede iniciar el partido.");
        }
        partido.attach(notificadorService);

        partido.setEstadoActual(getEstadoPartidoDesdeString(partido.getEstado()));

        partido.getEstadoActual().iniciar(partido);

        Partido partidoActualizado = partidoRepository.save(partido);

        return mapToDTO(partidoActualizado);
    }

    @Override
    public PartidoDTO finalizarPartido(Long partidoId, Long idUsuarioActual) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + partidoId));

        Usuario usuario = usuarioRepository.findById(idUsuarioActual)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuarioActual));

        if (!partido.getOrganizador().getId().equals(usuario.getId())) {
            throw new IllegalStateException("Solo el organizador puede finalizar el partido.");
        }
        partido.attach(notificadorService);

        partido.setEstadoActual(getEstadoPartidoDesdeString(partido.getEstado()));

        partido.getEstadoActual().finalizar(partido);

        Partido partidoActualizado = partidoRepository.save(partido);

        return mapToDTO(partidoActualizado);
    }

    private EstadoPartido getEstadoPartidoDesdeString(String estado) {
        switch (estado) {
            case "NECESITAMOS_JUGADORES":
                return new NecesitamosJugadores();
            case "PARTIDO_ARMADO":
                return new PartidoArmado();
            case "PARTIDO_CONFIRMADO":
                return new PartidoConfirmado();
            case "PARTIDO_EN_JUEGO":
                return new PartidoEnJuego();
            case "PARTIDO_FINALIZADO":
                return new PartidoFinalizado();
            case "PARTIDO_CANCELADO":
                return new PartidoCancelado();
            default:
                throw new IllegalStateException("Estado desconocido: " + estado);
        }
    }

    @Override
    public PartidoResponseDTO getPartidoWithDetails(Long id) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con ID: " + id));

        return mapToResponseDTO(partido);
    }

    @Override
    public List<UsuarioInfoDTO> sugerirJugadoresWithNames(Long partidoId, String criterio) {
        List<Usuario> usuarios = sugerirJugadores(partidoId, criterio);

        return usuarios.stream()
                .map(UsuarioInfoDTO::from)
                .toList();
    }

    private PartidoDTO mapToDTO(Partido partido) {
        return PartidoDTO.builder()
                .id(partido.getId())
                .organizadorId(partido.getOrganizador() != null ? partido.getOrganizador().getId() : null)
                .estado(partido.getEstado())
                .fechaHora(partido.getFechaHora())
                .ubicacionId(partido.getUbicacion() != null ? partido.getUbicacion().getId() : null)
                .deporteId(partido.getDeporte() != null ? partido.getDeporte().getId() : null)
                .jugadoresRequeridos(partido.getJugadoresRequeridos())
                .jugadoresIds(partido.getJugadores() != null
                        ? partido.getJugadores().stream().map(j -> j.getId()).toList()
                        : null)
                .build();
    }

    private PartidoResponseDTO mapToResponseDTO(Partido partido) {
        return PartidoResponseDTO.builder()
                .id(partido.getId())
                .organizador(UsuarioInfoDTO.from(partido.getOrganizador()))
                .estado(partido.getEstado())
                .fechaHora(partido.getFechaHora())
                .ubicacion(partido.getUbicacion() != null ? UbicacionDTO.builder()
                        .latitud(partido.getUbicacion().getLatitud())
                        .longitud(partido.getUbicacion().getLongitud())
                        .build() : null)
                .deporte(DeporteInfoDTO.from(partido.getDeporte()))
                .jugadoresRequeridos(partido.getJugadoresRequeridos())
                .jugadores(partido.getJugadores() != null
                        ? partido.getJugadores().stream()
                                .map(UsuarioInfoDTO::from)
                                .toList()
                        : null)
                .build();
    }

    /**
     * Valida que el organizador no tenga un partido en exactamente la misma fecha y
     * hora.
     * Esto previene spam de partidos duplicados.
     */
    private void validarConflictoDeHorario(Usuario organizador, java.time.LocalDateTime fechaHora) {
        if (fechaHora == null) {
            return; // Si no hay fecha, no hay conflicto
        }

        // Buscar partidos del mismo organizador en la misma fecha y hora exactas
        // Usar findAll() pero con filtros eficientes en memoria
        boolean tieneConflicto = partidoRepository.findAll()
                .stream()
                .anyMatch(partido -> partido.getOrganizador() != null &&
                        partido.getOrganizador().getId().equals(organizador.getId()) &&
                        partido.getFechaHora() != null &&
                        partido.getFechaHora().equals(fechaHora) &&
                        !esPartidoCancelado(partido.getEstado()));

        if (tieneConflicto) {
            throw new IllegalStateException(
                    "Ya tienes un partido programado para exactamente esa fecha y hora. " +
                            "Por favor, elige un horario diferente.");
        }
    }

    /**
     * Verifica si un partido está en estado cancelado
     */
    private boolean esPartidoCancelado(String estado) {
        return "PARTIDO_CANCELADO".equals(estado);
    }

}
