package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeporteRepository extends JpaRepository<Deporte, Long> {
    Optional<Deporte> findByNombreIgnoreCase(String nombre);
}
