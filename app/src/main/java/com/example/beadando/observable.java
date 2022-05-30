package com.example.beadando;

public class observable extends java.util.Observable {
    private static observable instance = null;

    private observable() {
    }

    public static observable getInstance() {
        if (instance == null) {
            instance = new observable();
        }
        return instance;
    }

    public void sendData(Object data) {
        setChanged();
        notifyObservers(data);
    }
}
