package com.example.adoptmemovil;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConsultarUsuarioFragment extends Fragment {

    private TextView textNombre, textCorreo, textTelefono, textCiudad;
    private ImageView imageViewPreview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultar_usuario, container, false);
    }

    /*@Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNombre = view.findViewById(R.id.text_nombre);
        textCorreo = view.findViewById(R.id.text_correo);
        textTelefono = view.findViewById(R.id.text_telefono);
        textCiudad = view.findViewById(R.id.text_ciudad);
        imageViewPreview = view.findViewById(R.id.imageViewPreview);

         Aquí deberías obtener los datos reales del usuario
        String nombre = "Juan Pérez";
        String correo = "juan.perez@email.com";
        String telefono = "555-1234567";
        String ciudad = "Ciudad de México";

        textNombre.setText("Nombre: " + nombre);
        textCorreo.setText("Correo: " + correo);
        textTelefono.setText("Teléfono: " + telefono);
        textCiudad.setText("Ciudad: " + ciudad);

        /* Si tienes una URI o imagen guardada, la puedes mostrar así
        // imageViewPreview.setImageURI(imagenUri);
        // O una imagen por defecto:
        Drawable avatar = getResources().getDrawable(R.drawable.avatar_por_defecto); // Reemplaza por tu imagen
        imageViewPreview.setImageDrawable(avatar);
    }*/
}

