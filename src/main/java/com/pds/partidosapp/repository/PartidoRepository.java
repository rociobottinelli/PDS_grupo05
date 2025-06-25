package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    // Acá podés agregar métodos de búsqueda personalizada más adelante si lo necesitás
}
