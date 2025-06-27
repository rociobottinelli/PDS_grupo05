package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacionPorMail implements EstrategiaDeNotificacion {

    private final IAdapterEmailNotification adapter;

    @Override
    public void enviar(Notificacion notificacion) {
        if (notificacion == null || notificacion.getUsuario() == null) {
            throw new IllegalArgumentException("La notificación y el usuario no pueden ser nulos");
        }
        log.info("Notificación enviada por Mail al usuario: {}",
                notificacion.getUsuario().getMail());
        log.info("Mensaje: {}", notificacion.getMensaje());

        if (notificacion != null)
        {
            return;
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
