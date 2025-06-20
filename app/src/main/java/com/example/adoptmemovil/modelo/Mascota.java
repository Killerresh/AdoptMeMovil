package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mascota implements Serializable {

    @SerializedName("MascotaID")
    private int mascotaID;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Especie")
    private String especie;

    @SerializedName("Raza")
    private String raza;

    @SerializedName("Edad")
    private String edad;

    @SerializedName("Sexo")
    private String sexo;

    @SerializedName("Tamaño")
    private String tamaño;

    @SerializedName("Descripcion")
    private String descripcion;

    // Campo auxiliar no proveniente del backend
    private String fotoUrl;

    public int getMascotaID() {
        return mascotaID;
    }

    public void setMascotaID(int mascotaID) {
        this.mascotaID = mascotaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
