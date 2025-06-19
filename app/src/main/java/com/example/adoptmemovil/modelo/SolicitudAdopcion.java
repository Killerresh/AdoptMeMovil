package com.example.adoptmemovil.modelo;

import com.google.gson.annotations.SerializedName;

public class SolicitudAdopcion {
    @SerializedName("SolicitudAdopcionID")
    private int solicitudAdopcionID;
    @SerializedName("Estado")
    private boolean estado;
    @SerializedName("MascotaID")
    private int mascotaID;
    @SerializedName("AdoptanteID")
    private Integer adoptanteID;
    @SerializedName("PublicadorID")
    private int publicadorID;
    @SerializedName("UbicacionID")
    private int ubicacionID;

    public int getSolicitudAdopcionID() {
        return solicitudAdopcionID;
    }

    public void setSolicitudAdopcionID(int solicitudAdopcionID) {
        this.solicitudAdopcionID = solicitudAdopcionID;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getMascotaID() {
        return mascotaID;
    }

    public void setMascotaID(int mascotaID) {
        this.mascotaID = mascotaID;
    }

    public Integer getAdoptanteID() {
        return adoptanteID;
    }

    public void setAdoptanteID(Integer adoptanteID) {
        this.adoptanteID = adoptanteID;
    }

    public int getPublicadorID() {
        return publicadorID;
    }

    public void setPublicadorID(int publicadorID) {
        this.publicadorID = publicadorID;
    }

    public int getUbicacionID() {
        return ubicacionID;
    }

    public void setUbicacionID(int ubicacionID) {
        this.ubicacionID = ubicacionID;
    }
}
