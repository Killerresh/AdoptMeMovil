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
        SolicitudAdopcion solicitudAdopcion = listaSolicitudes.get(position);
        Mascota mascota = solicitudAdopcion.getMascota();

        if (mascota != null) {
            holder.txtNombreMascota.setText(mascota.getNombre());

            String especieRazaEdad = String.format("%s · %s · %s",
                    mascota.getEspecie() != null ? mascota.getEspecie() : "N/D",
                    mascota.getRaza() != null ? mascota.getRaza() : "N/D",
                    mascota.getEdad() != null ? mascota.getEdad() : "N/D");
            holder.txtEspecieRazaEdad.setText(especieRazaEdad);

            if (solicitudAdopcion.isEstado()) {
                holder.txtEstadoSolicitud.setText("Estado: Adoptado");
                holder.txtEstadoSolicitud.setTextColor(Color.parseColor("#BB0000"));
            } else {
                holder.txtEstadoSolicitud.setText("Estado: Disponible");
                holder.txtEstadoSolicitud.setTextColor(Color.parseColor("#007700"));
            }

            if (mascota.getFotoUrl() != null && !mascota.getFotoUrl().isEmpty()) {
                Glide.with(context)
                        .load(mascota.getFotoUrl())
                        .placeholder(R.drawable.defaultpet)
                        .error(R.drawable.defaultpet)
                        .into(holder.imgFotoMascota);
            } else {
                holder.imgFotoMascota.setImageResource(R.drawable.defaultpet);
            }
        } else {
            holder.txtNombreMascota.setText("N/D");
            holder.txtEspecieRazaEdad.setText("N/D · N/D · N/D");
            holder.txtEstadoSolicitud.setText("Estado: N/D");
            holder.imgFotoMascota.setImageResource(R.drawable.defaultpet);
        }

        // Eliminar (pendiente)
        holder.btnEliminar.setOnClickListener(v -> {
            // TODO: lógica para eliminar
        });

        // Botón "Solicitudes pendientes"
        holder.btnSolicitudesPendientes.setOnClickListener(v -> {
            if (context instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) context;

                int adopcionID = solicitudAdopcion.getSolicitudAdopcionID(); // Asegúrate de tener el getter correcto

                // Llama al fragmento y le pasa la adopcionID
                SolicitudesPendientesDialogFragment dialog = SolicitudesPendientesDialogFragment.newInstance(adopcionID);
                dialog.show(activity.getSupportFragmentManager(), "SolicitudesPendientesDialog");
            }
        });

        // Botón "Detalles de mascota"
        holder.btnDetallesMascota.setOnClickListener(v -> {
            if (context instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) context;
                Bundle bundle = new Bundle();
                bundle.putSerializable("mascota", solicitudAdopcion.getMascota()); // Mascota debe ser Serializable

                ConsultarAdopcionFragment fragment = new ConsultarAdopcionFragment();
                fragment.setArguments(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
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
