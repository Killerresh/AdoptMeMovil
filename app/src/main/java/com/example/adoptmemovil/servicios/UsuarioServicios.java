package com.example.adoptmemovil.servicios;

import com.example.adoptmemovil.modelo.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioServicios {
    @POST("usuarios")
    Call<ResponseBody> registrarUsuario(@Body Usuario nuevoUsuario);
}
