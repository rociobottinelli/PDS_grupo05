package com.pds.partidosapp.service;

import com.pds.partidosapp.model.Notificacion;

public interface EstrategiaDeNotificacion {
    void enviar(Notificacion notificacion);
}
