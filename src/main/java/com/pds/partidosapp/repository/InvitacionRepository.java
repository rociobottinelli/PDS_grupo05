package com.pds.partidosapp.repository;

import com.pds.partidosapp.model.entity.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Invitacion.
 * Proporciona métodos para acceder y gestionar las invitaciones en la base de datos.
 */
@Repository
public interface InvitacionRepository extends JpaRepository<Invitacion, Long> {
    // Se pueden agregar métodos personalizados de consulta aquí si son necesarios
}
