package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Estrategia de notificación que simula el envío de notificaciones a través de Firebase.
 * En una implementación real, se integraría con Firebase Cloud Messaging (FCM).
 */
@Slf4j
@Component
public class NotificarPorFirebase implements EstrategiaDeNotificacion {

    @Override
    public void enviar(Notificacion notificacion) {
        if (notificacion == null || notificacion.getUsuario() == null) {
            throw new IllegalArgumentException("La notificación y el usuario no pueden ser nulos");
        }

        log.info("Notificación enviada por Firebase al usuario: {}", 
                notificacion.getUsuario().getMail());
        log.info("Mensaje: {}", notificacion.getMensaje());
        
        System.out.println("Notificación enviada por Firebase");
    }
}
