package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

public class Solicitud {
    @SerializedName("SolicitudID")
    private int solicitudID;

    @SerializedName("AdoptanteID")
    private int adoptanteID;

    @SerializedName("AdopcionID")
    private int adopcionID;

    @SerializedName("NombreAdoptante")
    private String nombreUsuarioAdoptante;  // Nuevo campo

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

    public String getNombreUsuarioAdoptante() {
        return nombreUsuarioAdoptante;
    }

    public void setNombreUsuarioAdoptante(String nombreUsuarioAdoptante) {
        this.nombreUsuarioAdoptante = nombreUsuarioAdoptante;
    }
}
