package com.example.adoptmemovil.utilidades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adoptmemovil.R;
import com.example.adoptmemovil.modelo.Solicitud;
import com.example.adoptmemovil.servicios.ClienteAPI;
import com.example.adoptmemovil.servicios.SolicitudServicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SolicitudesPendientesDialogFragment extends DialogFragment {

    private static final String ARG_ADOPCION_ID = "adopcion_id";

    private int adopcionID;
    private List<Solicitud> listaSolicitudesPendientes = new ArrayList<>();
    private SolicitudesPendientesAdapter.OnSolicitudActionListener actionListener;

    private RecyclerView recyclerView;
    private SolicitudesPendientesAdapter adapter;
    private TextView tvMensajeVacio;


    public static SolicitudesPendientesDialogFragment newInstance(int adopcionID) {
        SolicitudesPendientesDialogFragment fragment = new SolicitudesPendientesDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ADOPCION_ID, adopcionID);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnSolicitudActionListener(SolicitudesPendientesAdapter.OnSolicitudActionListener listener) {
        this.actionListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_solicitudes_pendientes, container, false);
        tvMensajeVacio = view.findViewById(R.id.tvMensajeVacio);

        if (getArguments() != null) {
            adopcionID = getArguments().getInt(ARG_ADOPCION_ID, -1);
        }

        recyclerView = view.findViewById(R.id.recyclerSolicitudesPendientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SolicitudesPendientesAdapter(listaSolicitudesPendientes, actionListener);
        recyclerView.setAdapter(adapter);

        cargarSolicitudesPendientes(adopcionID);

        return view;
    }

    private void cargarSolicitudesPendientes(int adopcionID) {
        SolicitudServicios servicio = ClienteAPI.getRetrofit()
                .create(SolicitudServicios.class);

        Call<List<Solicitud>> call = servicio.obtenerSolicitudesConNombresPorAdopcionID(adopcionID);

        call.enqueue(new retrofit2.Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, retrofit2.Response<List<Solicitud>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaSolicitudesPendientes.clear();
                    listaSolicitudesPendientes.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (listaSolicitudesPendientes.isEmpty()) {
                        // Mostrar mensaje y ocultar recycler
                        tvMensajeVacio.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        // Ocultar mensaje y mostrar recycler
                        tvMensajeVacio.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Manejar respuesta vacía o error, por ejemplo mostrar mensaje vacío
                    tvMensajeVacio.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                // Manejar fallo de conexión
                // Por ejemplo: mostrar un Toast o mensaje en pantalla
            }
        });
    }
}
