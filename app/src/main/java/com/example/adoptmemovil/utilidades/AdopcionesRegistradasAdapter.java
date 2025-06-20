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
import com.example.adoptmemovil.modelo.SolicitudAdopcion;

import java.util.List;

public class AdopcionesRegistradasAdapter extends RecyclerView.Adapter<AdopcionesRegistradasAdapter.ViewHolder> {

    private Context context;
    private List<SolicitudAdopcion> listaSolicitudes;

    public AdopcionesRegistradasAdapter(Context context, List<SolicitudAdopcion> listaSolicitudes) {
        this.context = context;
        this.listaSolicitudes = listaSolicitudes;
    }

    @NonNull
    @Override
    public AdopcionesRegistradasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adopcion_registrada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdopcionesRegistradasAdapter.ViewHolder holder, int position) {
        SolicitudAdopcion solicitud = listaSolicitudes.get(position);
        Mascota mascota = solicitud.getMascota();

        if (mascota != null) {
            holder.txtNombreMascota.setText(mascota.getNombre());

            // Concatenar Especie · Raza · Edad
            String especieRazaEdad = String.format("%s · %s · %s",
                    mascota.getEspecie() != null ? mascota.getEspecie() : "N/D",
                    mascota.getRaza() != null ? mascota.getRaza() : "N/D",
                    mascota.getEdad() != null ? mascota.getEdad() : "N/D");
            holder.txtEspecieRazaEdad.setText(especieRazaEdad);

            // Estado: false = Disponible, true = Adoptado
            if (solicitud.isEstado()) {
                holder.txtEstadoSolicitud.setText("Estado: Adoptado");
                holder.txtEstadoSolicitud.setTextColor(Color.parseColor("#BB0000")); // verde
            } else {
                holder.txtEstadoSolicitud.setText("Estado: Disponible");
                holder.txtEstadoSolicitud.setTextColor(Color.parseColor("#007700"));
            }

            // Cargar la foto con Glide, ajustar si el campo es diferente
            if (mascota.getFotoUrl() != null && !mascota.getFotoUrl().isEmpty()) {
                Glide.with(context)
                        .load(mascota.getFotoUrl())
                        .placeholder(R.drawable.defaultpet)  // pon tu imagen placeholder aquí
                        .error(R.drawable.defaultpet)        // o la imagen de error
                        .into(holder.imgFotoMascota);
            } else {
                holder.imgFotoMascota.setImageResource(R.drawable.defaultpet);
            }
        } else {
            // Si no hay mascota, poner valores por defecto
            holder.txtNombreMascota.setText("N/D");
            holder.txtEspecieRazaEdad.setText("N/D · N/D · N/D");
            holder.txtEstadoSolicitud.setText("Estado: N/D");
            holder.imgFotoMascota.setImageResource(R.drawable.defaultpet);
        }

        // Botones sin funcionalidad por ahora
        holder.btnEliminar.setOnClickListener(v -> { /* TODO */ });
        holder.btnSolicitudesPendientes.setOnClickListener(v -> { /* TODO */ });
        holder.btnDetallesMascota.setOnClickListener(v -> {
            // Para abrir el fragmento con la info de la mascota
            if (context instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) context;
                Bundle bundle = new Bundle();
                bundle.putSerializable("mascota", solicitud.getMascota()); // Mascota debe implementar Serializable o Parcelable

                ConsultarAdopcionFragment fragment = new ConsultarAdopcionFragment();
                fragment.setArguments(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment) // Ajusta el container id según tu layout
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaSolicitudes != null ? listaSolicitudes.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreMascota, txtEspecieRazaEdad, txtEstadoSolicitud;
        ImageView imgFotoMascota;
        Button btnEliminar, btnSolicitudesPendientes, btnDetallesMascota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreMascota = itemView.findViewById(R.id.txtNombreMascota);
            txtEspecieRazaEdad = itemView.findViewById(R.id.txtEspecieRazaEdad);
            txtEstadoSolicitud = itemView.findViewById(R.id.txtEstadoSolicitud);
            imgFotoMascota = itemView.findViewById(R.id.imgFotoMascota);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnSolicitudesPendientes = itemView.findViewById(R.id.btnSolicitudesPendientes);
            btnDetallesMascota = itemView.findViewById(R.id.btnDetallesMascota);
        }
    }
}
