package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("UsuarioID")
    public int usuarioID;
    @SerializedName("Nombre")
    public String nombre;
    @SerializedName("Telefono")
    public String telefono;
    @SerializedName("Ubicacion")
    public Ubicacion ubicacion;
    @SerializedName("Acceso")
    public Acceso acceso;

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }
}
