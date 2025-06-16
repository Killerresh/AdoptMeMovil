package com.example.adoptmemovil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegistrarAdopcionFragment extends Fragment {

    private EditText inputNombre, inputEspecie, inputRaza, inputAno, inputMes,
            inputSexo, inputTamano, inputDescripcion;
    private Button btnSubirFoto, btnRegistrar;
    private ImageView imageViewPreview;

    private Uri imagenSeleccionada = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                imagenSeleccionada = result.getData().getData();
                                imageViewPreview.setImageURI(imagenSeleccionada);
                                imageViewPreview.setVisibility(View.VISIBLE);
                            }
                        }
                    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrar_adopcion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputNombre = view.findViewById(R.id.input_nombre);
        inputEspecie = view.findViewById(R.id.input_especie);
        inputRaza = view.findViewById(R.id.input_raza);
        inputAno = view.findViewById(R.id.input_ano);
        inputMes = view.findViewById(R.id.input_mes);
        inputSexo = view.findViewById(R.id.input_sexo);
        inputTamano = view.findViewById(R.id.input_tamano);
        inputDescripcion = view.findViewById(R.id.input_descripcion);
        btnSubirFoto = view.findViewById(R.id.btn_subir_foto);
        btnRegistrar = view.findViewById(R.id.btn_registrar);
        imageViewPreview = view.findViewById(R.id.imageViewPreview);

        // Ocultar preview al inicio
        imageViewPreview.setVisibility(View.GONE);

        btnSubirFoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            imagePickerLauncher.launch(Intent.createChooser(intent, "Selecciona una imagen"));
        });

        btnRegistrar.setOnClickListener(v -> registrarAdopcion());
    }

    private void registrarAdopcion() {
        String nombre = inputNombre.getText().toString().trim();
        String especie = inputEspecie.getText().toString().trim();
        String raza = inputRaza.getText().toString().trim();
        String ano = inputAno.getText().toString().trim();
        String mes = inputMes.getText().toString().trim();
        String sexo = inputSexo.getText().toString().trim();
        String tamano = inputTamano.getText().toString().trim();
        String descripcion = inputDescripcion.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(especie) || TextUtils.isEmpty(raza)
                || TextUtils.isEmpty(ano) || TextUtils.isEmpty(mes)
                || TextUtils.isEmpty(sexo) || TextUtils.isEmpty(tamano)
                || TextUtils.isEmpty(descripcion)) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imagenSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int anio = Integer.parseInt(ano);
            int mesNum = Integer.parseInt(mes);
            if (anio < 0 || mesNum < 1 || mesNum > 12) {
                Toast.makeText(getContext(), "Año o mes inválidos", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Año y mes deben ser numéricos", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Lógica para guardar los datos
        Toast.makeText(getContext(), "Mascota registrada exitosamente", Toast.LENGTH_SHORT).show();
    }
}
