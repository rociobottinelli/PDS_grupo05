package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.UsuarioDeporte;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.enums.NivelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioDeporteRepository extends JpaRepository<UsuarioDeporte, Long> {
    
    @Query("SELECT ud.usuario FROM UsuarioDeporte ud " +
           "WHERE ud.deporteId = :deporteId AND ud.activo = true")
    List<Usuario> findUsuariosByDeporteId(@Param("deporteId") Long deporteId);
    
    @Query("SELECT ud.usuario FROM UsuarioDeporte ud " +
           "WHERE ud.deporteId = :deporteId " +
           "AND ud.nivelEnDeporte = :nivel " +
           "AND ud.activo = true")
    List<Usuario> findUsuariosByDeporteIdAndNivel(
        @Param("deporteId") Long deporteId,
        @Param("nivel") NivelEnum nivel
    );
    
    @Query("SELECT CASE WHEN COUNT(ud) > 0 THEN true ELSE false END FROM UsuarioDeporte ud " +
           "WHERE ud.usuario.id = :usuarioId AND ud.deporteId = :deporteId AND ud.activo = true")
    boolean existsByUsuarioIdAndDeporteIdAndActivoTrue(
        @Param("usuarioId") Long usuarioId,
        @Param("deporteId") Long deporteId
    );
}
