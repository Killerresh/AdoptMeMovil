package com.example.adoptmemovil.servicios;

import com.example.adoptmemovil.modelo.SolicitudAdopcion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SolicitudAdopcionServicios {
    @POST("solicitudAdopciones")
    Call<ResponseBody> registrarSolicitudAdopcion(@Body SolicitudAdopcion solicitudAdopcion);
}
