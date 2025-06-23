package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
  Optional<Ubicacion> findById(Long id);

  @Query("SELECT u FROM Ubicacion u WHERE u.latitud BETWEEN :latMin AND :latMax " +
      "AND u.longitud BETWEEN :lonMin AND :lonMax")
  List<Ubicacion> findByProximidad(@Param("latMin") Double latMin,
      @Param("latMax") Double latMax,
      @Param("lonMin") Double lonMin,
      @Param("lonMax") Double lonMax);
}