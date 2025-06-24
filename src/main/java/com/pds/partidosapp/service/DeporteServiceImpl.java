package com.pds.partidosapp.service;

import com.pds.partidosapp.dto.DeporteDTO;
import com.pds.partidosapp.shared.exceptions.DuplicateException;
import com.pds.partidosapp.model.entity.Deporte;
import com.pds.partidosapp.repository.DeporteRepository;
import com.pds.partidosapp.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeporteServiceImpl implements IDeporteService {

    @Autowired
    private final DeporteRepository deporteRepository;

    @Override
    public Deporte crearDeporte(DeporteDTO dto) {
        deporteRepository.findByNombreIgnoreCase(dto.getNombre())
                .ifPresent(d -> {
                    throw new DuplicateException("Ya existe un deporte con el nombre: " + dto.getNombre());
                });
        Deporte deporte = Deporte.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .cantidadJugadores(dto.getCantidadJugadores())
                .build();
        return deporteRepository.save(deporte);
    }

    @Override
    public Deporte actualizarDeporte(Long id, DeporteDTO dto) {
        Deporte existente = deporteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deporte no encontrado con id: " + id));

        deporteRepository.findByNombreIgnoreCase(dto.getNombre())
                .filter(d -> !d.getId().equals(id)) // Si hay otro con el mismo nombre
                .ifPresent(d -> {
                    throw new DuplicateException("Ya existe otro deporte con el nombre: " + dto.getNombre());
                });

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setCantidadJugadores(dto.getCantidadJugadores());

        return deporteRepository.save(existente);
    }

    @Override
    public void eliminarDeporte(Long id) {
        deporteRepository.deleteById(id);
    }

    @Override
    public List<Deporte> listarDeportes() {
        return deporteRepository.findAll();
    }
}
