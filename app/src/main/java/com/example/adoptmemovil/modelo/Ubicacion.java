package com.example.adoptmemovil.modelo;

public class Ubicacion {
    public Integer ubicacionID;
    public String ciudad;
    public String estado;
    public String pais;
    public Double longitud;
    public Double latitud;

    public Integer getUbicacionID() {
        return ubicacionID;
    }

    public void setUbicacionID(Integer ubicacionID) {
        this.ubicacionID = ubicacionID;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ciudad: ").append(ciudad).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("País: ").append(pais).append("\n");
        sb.append("Latitud: ").append(latitud).append("\n");
        sb.append("Longitud: ").append(longitud).append("\n");

        return sb.toString();
    }
}
