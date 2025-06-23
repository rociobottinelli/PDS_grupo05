package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.enums.NivelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        Optional<Usuario> findByIdAndActivoTrue(Long id);

        Optional<Usuario> findByEmail(String email);

        boolean existsByEmail(String email);

        boolean existsByNombreUsuario(String nombreUsuario);

        // Lista activos con filtros deportivos (UNIFICADO + UBICACIÓN)
        @Query("SELECT u FROM Usuario u WHERE u.activo = true " +
                        "AND (:nivel IS NULL OR u.nivelJuego = :nivel) " +
                        "AND (:edadMin IS NULL OR u.edad >= :edadMin) " +
                        "AND (:edadMax IS NULL OR u.edad <= :edadMax) " +
                        "AND (:ubicacionId IS NULL OR u.ubicacion.id = :ubicacionId)")
        List<Usuario> findUsuariosActivos(@Param("nivel") NivelEnum nivel,
                        @Param("edadMin") Integer edadMin,
                        @Param("edadMax") Integer edadMax,
                        @Param("ubicacionId") Long ubicacionId);
}
