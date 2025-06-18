package com.example.adoptmemovil.modelo;

public class SolicitudAdopcion {
    public int solicitudAdopcionID;
    public boolean estado;
    public int mascotaID;
    public Integer adoptanteID;
    public int publicadorID;
    public int ubicacionID;

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
