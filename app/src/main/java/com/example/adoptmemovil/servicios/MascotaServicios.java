package com.example.adoptmemovil.servicios;

import com.example.adoptmemovil.modelo.Mascota;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MascotaServicios {
    @POST("mascotas")
    Call<ResponseBody> registrarMascota(@Body Mascota mascota);
}

