package com.example.adoptmemovil.modelo;

public class Acceso {
    public int accesoID;
    public String correo;
    public String contrasenaHash;
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
