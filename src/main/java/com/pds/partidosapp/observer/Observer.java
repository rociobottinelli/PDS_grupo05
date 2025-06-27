package com.pds.partidosapp.observer;

import com.pds.partidosapp.model.entity.Partido;

public interface Observer {
    void update(Partido partido);
}
