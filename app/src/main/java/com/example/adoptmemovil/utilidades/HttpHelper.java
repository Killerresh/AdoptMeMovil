package com.example.adoptmemovil.utilidades;

import com.example.adoptmemovil.modelo.ResultadoHttp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpHelper {
    public static <T> void ejecutarHttp(Call<T> call, HttpCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                ResultadoHttp<T> resultado = new ResultadoHttp<>();
                resultado.codigo = response.code();

                if (response.isSuccessful()) {
                    resultado.exito = true;
                    resultado.contenido = response.body();
                } else if (response.code() == 401) {
                    resultado.exito = false;
                    resultado.mensajeError = "No estás autenticado";
                } else if (response.code() == 503) {
                    resultado.exito = false;
                    resultado.mensajeError = "Error en la base de datos";
                } else {
                    resultado.exito = false;
                    resultado.mensajeError = "Error en el servidor";
                }

                callback.onRespuesta(resultado);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                ResultadoHttp<T> resultado = new ResultadoHttp<>();
                resultado.exito = false;
                resultado.mensajeError = "Error de red o conexión: " + t.getMessage();
                callback.onRespuesta(resultado);
            }
        });
    }
}
