package com.example.adoptmemovil.utilidades;

import com.example.adoptmemovil.modelo.Usuario;

public class UsuarioSingleton {
    private Usuario usuarioActual;
    private String token;

    private UsuarioSingleton() { }

    private static class Holder {
        private static final UsuarioSingleton INSTANCIA = new UsuarioSingleton();
    }

    public static UsuarioSingleton getInstancia() {
        return Holder.INSTANCIA;
    }

    public void iniciarSesion(Usuario usuario, String token) {
        this.usuarioActual = usuario;
        this.token = token;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
        this.token = null;
    }

    public boolean estaAutenticado() {
        return token != null && !token.isEmpty();
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String getToken() {
        return token;
    }
}
