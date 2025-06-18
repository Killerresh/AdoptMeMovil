package com.example.adoptmemovil.modelo;

public class SolicitudAdopcion {
    private int solicitudAdopcionID;
    private boolean estado;
    private Integer adoptanteID;
    private int publicadorID;

    private Mascota mascota;
    private Ubicacion ubicacion;

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

    // Getters y setters para Mascota
    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    // Getters y setters para Ubicacion
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
