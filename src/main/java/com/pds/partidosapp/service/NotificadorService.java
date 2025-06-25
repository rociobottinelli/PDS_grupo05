package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para el manejo de notificaciones.
 * Permite enviar notificaciones utilizando diferentes estrategias.
 */
@Service
public class NotificadorService implements INotificadorService {

    private final IAdapterEmailNotification adapter;
    private EstrategiaDeNotificacion estrategiaDeNotificacion;

    /**
     * Constructor que inyecta el adaptador de notificación por correo electrónico.
     * 
     * @param adapter Adaptador para el envío de correos electrónicos
     */
    @Autowired
    public NotificadorService(IAdapterEmailNotification adapter) {
        this.adapter = adapter;
    }

    /**
     * Envía una notificación utilizando la estrategia configurada.
     * Si no se ha configurado ninguna estrategia, no se realizará ninguna acción.
     * 
     * @param notificacion La notificación a enviar
     */
    @Override
    public void enviar(Notificacion notificacion) {
        if (estrategiaDeNotificacion != null) {
            estrategiaDeNotificacion.enviar(notificacion);
        }
    }

    /**
     * Cambia la estrategia de notificación que se utilizará para enviar las notificaciones.
     * 
     * @param estrategia La nueva estrategia de notificación a utilizar
     */
    @Override
    public void cambiarEstrategiaDeNotificacion(EstrategiaDeNotificacion estrategia) {
        this.estrategiaDeNotificacion = estrategia;
    }

    /**
     * Obtiene la estrategia de notificación actual.
     * 
     * @return La estrategia de notificación actual o null si no se ha configurado ninguna
     */
    @Override
    public EstrategiaDeNotificacion getEstrategiaDeNotificacion() {
        return estrategiaDeNotificacion;
    }
}
