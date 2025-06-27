package com.pds.partidosapp.shared;

import com.pds.partidosapp.model.entity.Partido;

public interface Observer {
    void update(Partido partido);
}
