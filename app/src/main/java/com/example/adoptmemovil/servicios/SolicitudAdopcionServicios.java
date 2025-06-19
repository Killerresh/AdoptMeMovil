package com.example.adoptmemovil.servicios;

import com.example.adoptmemovil.modelo.SolicitudAdopcion;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SolicitudAdopcionServicios {

    @POST("solicitudAdopciones")
    Call<ResponseBody> registrarSolicitudAdopcion(@Body SolicitudAdopcion solicitudAdopcion);

    @GET("solicitudAdopciones/aceptadas")
    Call<List<SolicitudAdopcion>> obtenerSolicitudesAceptadas();

    @GET("solicitudAdopciones/pendientes")
    Call<List<SolicitudAdopcion>> obtenerSolicitudesPendientes();
}
