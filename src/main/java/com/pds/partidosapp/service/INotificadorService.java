package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;

/**
 * Interfaz que define el contrato para el servicio de notificaciones.
 * Proporciona métodos para enviar notificaciones y gestionar estrategias de notificación.
 */
public interface INotificadorService {
    
    /**
     * Envía una notificación utilizando la estrategia configurada.
     * Si no se ha configurado ninguna estrategia, no se realizará ninguna acción.
     * 
     * @param notificacion La notificación a enviar
     */
    void enviar(Notificacion notificacion);
    
    /**
     * Cambia la estrategia de notificación que se utilizará para enviar las notificaciones.
     * 
     * @param estrategia La nueva estrategia de notificación a utilizar
     */
    void cambiarEstrategiaDeNotificacion(EstrategiaDeNotificacion estrategia);
    
    /**
     * Obtiene la estrategia de notificación actual.
     * 
     * @return La estrategia de notificación actual o null si no se ha configurado ninguna
     */
    EstrategiaDeNotificacion getEstrategiaDeNotificacion();
}
