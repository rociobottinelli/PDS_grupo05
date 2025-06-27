package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.Comentario;
import com.pds.partidosapp.model.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPartido(Partido partido);
}