package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;

/**
 * Interfaz que define el contrato para las estrategias de notificación.
 * Cada implementación debe proporcionar la lógica para enviar una notificación.
 */
public interface EstrategiaDeNotificacion {
    
    /**
     * Envía una notificación utilizando la estrategia implementada.
     * 
     * @param notificacion La notificación a enviar
     */
    void enviar(Notificacion notificacion);
}
