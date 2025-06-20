package com.example.adoptmemovil.utilidades;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adoptmemovil.R;
import com.example.adoptmemovil.modelo.Mascota;
import com.example.adoptmemovil.modelo.Solicitud;

import java.util.List;

public class SolicitudesPendientesAdapter extends RecyclerView.Adapter<SolicitudesPendientesAdapter.ViewHolder> {

    public interface OnSolicitudActionListener {
        void onAceptar(int posicion);
        void onRechazar(int posicion);
    }

    private List<Solicitud> listaSolicitudesPendientes;
    private OnSolicitudActionListener listener;

    public SolicitudesPendientesAdapter(List<Solicitud> lista, OnSolicitudActionListener listener) {
        this.listaSolicitudesPendientes = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SolicitudesPendientesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solicitud_pendiente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudesPendientesAdapter.ViewHolder holder, int position) {
        Solicitud solicitud = listaSolicitudesPendientes.get(position);

        // Poner nombre de usuario del adoptante (asumiendo que hay un método getAdoptanteNombre())
        // Si no tienes ese método, lo ideal es agregarlo o pasarlo desde el modelo.
        String nombreUsuario = solicitud.getNombreUsuarioAdoptante();
        holder.txtNombreUsuario.setText(nombreUsuario != null ? nombreUsuario : "N/D");

        holder.btnAceptar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAceptar(position);
            }
        });

        holder.btnRechazar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRechazar(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSolicitudesPendientes != null ? listaSolicitudesPendientes.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnAceptar, btnRechazar;
        ImageView imgPerfilDefecto;
        TextView txtNombreUsuario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAceptar = itemView.findViewById(R.id.btnAceptar);
            btnRechazar = itemView.findViewById(R.id.btnRechazar);
            imgPerfilDefecto = itemView.findViewById(R.id.imgPerfilDefecto);
            txtNombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
        }
    }
}

