package com.example.adoptmemovil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdopcionesRegistradasFragment extends Fragment {

    private EditText inputNombre;
    private Button btnEliminar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adopciones_registradas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputNombre = view.findViewById(R.id.input_nombre);
        btnEliminar = view.findViewById(R.id.btn_eliminar);

        btnEliminar.setOnClickListener(v -> {
            String nombreMascota = inputNombre.getText().toString().trim();

            if (TextUtils.isEmpty(nombreMascota)) {
                Toast.makeText(getContext(), "Por favor seleccione a la mascota", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aquí iría la lógica para eliminar la mascota del sistema (ej: base de datos)
            // Por ahora, solo muestra un mensaje de éxito:
            Toast.makeText(getContext(), "Mascota '" + nombreMascota + "' eliminada correctamente", Toast.LENGTH_SHORT).show();
        });
    }
}
