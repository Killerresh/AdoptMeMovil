package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

public class Solicitud {
    @SerializedName("SolicitudID")
    public int solicitudID;

    @SerializedName("AdoptanteID")
    public int adoptanteID;

    @SerializedName("AdopcionID")
    public int adopcionID;

    // Getters y Setters

    public int getSolicitudID() {
        return solicitudID;
    }

    public void setSolicitudID(int solicitudID) {
        this.solicitudID = solicitudID;
    }

    public int getAdoptanteID() {
        return adoptanteID;
    }

    public void setAdoptanteID(int adoptanteID) {
        this.adoptanteID = adoptanteID;
    }

    public int getAdopcionID() {
        return adopcionID;
    }

    public void setAdopcionID(int adopcionID) {
        this.adopcionID = adopcionID;
    }
}
