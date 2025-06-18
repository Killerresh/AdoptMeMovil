package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

public class Mascota {
    @SerializedName("MascotaID")
    public int mascotaID;
    @SerializedName("Nombre")
    public String nombre;
    @SerializedName("Especie")
    public String especie;
    @SerializedName("Raza")
    public String raza;
    @SerializedName("Edad")
    public String edad;
    @SerializedName("Sexo")
    public String sexo;
    @SerializedName("Tamano")
    public String tamano;
    @SerializedName("Descripcion")
    public String descripcion;

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

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
