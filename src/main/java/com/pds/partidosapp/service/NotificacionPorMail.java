package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Estrategia de notificación que envía notificaciones por correo electrónico.
 * Utiliza un adaptador de correo electrónico para enviar los mensajes.
 */
@Component
@RequiredArgsConstructor
public class NotificacionPorMail implements EstrategiaDeNotificacion {

    private final IAdapterEmailNotification adapter;

    @Override
    public void enviar(Notificacion notificacion) {
        if (notificacion == null || notificacion.getUsuario() == null) {
            throw new IllegalArgumentException("La notificación y el usuario no pueden ser nulos");
        }

        String destinatario = notificacion.getUsuario().getMail();
        String asunto = "Notificación del Sistema";
        String mensaje = notificacion.getMensaje();
        
        try {
            adapter.sendEmail(destinatario, asunto, mensaje, true);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar la notificación por correo: " + e.getMessage(), e);
        }
    }
}
