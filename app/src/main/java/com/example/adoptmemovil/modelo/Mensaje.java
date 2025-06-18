package com.example.adoptmemovil.modelo;

public class Mensaje {
    public int mensajeID;
    public int remitenteID;
    public int receptorID;
    public String contenido;

    public int getMensajeID() {
        return mensajeID;
    }

    public void setMensajeID(int mensajeID) {
        this.mensajeID = mensajeID;
    }

    public int getRemitenteID() {
        return remitenteID;
    }

    public void setRemitenteID(int remitenteID) {
        this.remitenteID = remitenteID;
    }

    public int getReceptorID() {
        return receptorID;
    }

    public void setReceptorID(int receptorID) {
        this.receptorID = receptorID;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
