package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

public class Acceso {
    @SerializedName("AccesoID")
    public int accesoID;
    @SerializedName("Correo")
    public String correo;
    @SerializedName("ContrasenaHash")
    public String contrasenaHash;
    @SerializedName("EsAdmin")
    public boolean esAdmin;

    public int getAccesoID() {
        return accesoID;
    }

    public void setAccesoID(int accesoID) {
        this.accesoID = accesoID;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
}
