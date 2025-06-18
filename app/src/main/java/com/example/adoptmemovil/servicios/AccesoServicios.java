package com.example.adoptmemovil.servicios;

import com.example.adoptmemovil.modelo.request.IniciarSesionRequest;
import com.example.adoptmemovil.modelo.response.IniciarSesionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccesoServicios {
    @POST("acceso/iniciar-sesion")
    Call<IniciarSesionResponse> iniciarSesion(@Body IniciarSesionRequest request);
}
