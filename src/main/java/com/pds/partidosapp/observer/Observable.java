package com.pds.partidosapp.observer;

public interface Observable {
    void attach(Observer o);
    void detach(Observer o);
    void notificar();
}
