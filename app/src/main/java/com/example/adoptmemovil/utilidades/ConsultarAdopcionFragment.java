package com.example.adoptmemovil.utilidades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adoptmemovil.R;
import com.example.adoptmemovil.modelo.Mascota;

public class ConsultarAdopcionFragment extends Fragment {

    private TextView tvNombre, tvEspecie, tvRaza, tvEdad, tvSexo, tvEstatura, tvDescripcion;
    private ImageView imgFotoMascota;
    private Button btnVerVideo;
    private Mascota mascota;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultar_adopcion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNombre = view.findViewById(R.id.tvNombre);
        tvEspecie = view.findViewById(R.id.tvEspecie);
        tvRaza = view.findViewById(R.id.tvRaza);
        tvEdad = view.findViewById(R.id.tvEdad);
        tvSexo = view.findViewById(R.id.tvSexo);
        tvEstatura = view.findViewById(R.id.tvEstatura);
        tvDescripcion = view.findViewById(R.id.tvDescripcion);
        imgFotoMascota = view.findViewById(R.id.imgFotoMascota);
        btnVerVideo = view.findViewById(R.id.btnVerVideo);

        if (getArguments() != null) {
            mascota = (Mascota) getArguments().getSerializable("mascota");

            if (mascota != null) {
                tvNombre.setText("Nombre: " + mascota.getNombre());
                tvEspecie.setText("Especie: " + mascota.getEspecie());
                tvRaza.setText("Raza: " + mascota.getRaza());
                tvEdad.setText("Edad: " + mascota.getEdad());
                tvSexo.setText("Sexo: " + mascota.getSexo());
                tvEstatura.setText("Estatura: " + mascota.getTamaño());
                tvDescripcion.setText("Descripción: " + mascota.getDescripcion());

                Glide.with(requireContext())
                        .load(mascota.getFotoUrl())
                        .placeholder(R.drawable.defaultpet)
                        .error(R.drawable.defaultpet)
                        .into(imgFotoMascota);
            }
        }

        btnVerVideo.setOnClickListener(v -> {
            // Por ahora sin funcionalidad
        });
    }
}
