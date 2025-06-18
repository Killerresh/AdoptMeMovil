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

import com.example.adoptmemovil.modelo.Mascota;
import com.example.adoptmemovil.modelo.SolicitudAdopcion;
import com.example.adoptmemovil.modelo.Ubicacion;
import com.example.adoptmemovil.servicios.ClienteAPI;
import com.example.adoptmemovil.servicios.SolicitudAdopcionServicios;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarAdopcionFragment extends Fragment {

    private EditText inputNombre, inputEspecie, inputRaza, inputAno, inputMes,
            inputSexo, inputTamano, inputDescripcion, inputLatitud, inputLongitud,
            inputCiudad, inputEstado, inputPais;
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

        // Campos de ubicación
        inputLatitud.setText("19.541620");
        inputLongitud.setText("-96.932527");
        inputCiudad.setText("Xalapa");
        inputEstado.setText("Veracruz");
        inputPais.setText("Mexico");

        btnSubirFoto = view.findViewById(R.id.btn_subir_foto);
        btnRegistrar = view.findViewById(R.id.btn_registrar);
        imageViewPreview = view.findViewById(R.id.imageViewPreview);

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
        // Validar campos obligatorios mascota
        String nombre = inputNombre.getText().toString().trim();
        String especie = inputEspecie.getText().toString().trim();
        String raza = inputRaza.getText().toString().trim();
        String ano = inputAno.getText().toString().trim();
        String mes = inputMes.getText().toString().trim();
        String sexo = inputSexo.getText().toString().trim();
        String tamano = inputTamano.getText().toString().trim();
        String descripcion = inputDescripcion.getText().toString().trim();

        // Validar campos obligatorios ubicación
        String latStr = inputLatitud.getText().toString().trim();
        String lonStr = inputLongitud.getText().toString().trim();
        String ciudad = inputCiudad.getText().toString().trim();
        String estado = inputEstado.getText().toString().trim();
        String pais = inputPais.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(especie) || TextUtils.isEmpty(raza)
                || TextUtils.isEmpty(ano) || TextUtils.isEmpty(mes)
                || TextUtils.isEmpty(sexo) || TextUtils.isEmpty(tamano)) {
            Toast.makeText(getContext(), "Por favor completa todos los campos obligatorios de la mascota", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(latStr) || TextUtils.isEmpty(lonStr)
                || TextUtils.isEmpty(ciudad) || TextUtils.isEmpty(estado) || TextUtils.isEmpty(pais)) {
            Toast.makeText(getContext(), "Por favor completa todos los campos obligatorios de ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imagenSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitud, longitud;
        try {
            latitud = Double.parseDouble(latStr);
            longitud = Double.parseDouble(lonStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Latitud o longitud inválidas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir la edad concatenando años y meses (puedes ajustarlo si quieres)
        String edad = ano + " años " + mes + " meses";

        // Construir objeto Mascota
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setEdad(edad);
        mascota.setSexo(sexo);
        mascota.setTamano(tamano);
        mascota.setDescripcion(descripcion);

        // Construir objeto Ubicacion
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);
        ubicacion.setCiudad(ciudad);
        ubicacion.setEstado(estado);
        ubicacion.setPais(pais);

        // Construir objeto SolicitudAdopcion
        SolicitudAdopcion solicitud = new SolicitudAdopcion();

        // Aquí debes asignar estos IDs según tu lógica de usuario (ejemplo, hardcodeado)
        solicitud.setPublicadorID(1);  // Ajusta este valor dinámicamente según usuario autenticado
        solicitud.setAdoptanteID(2);   // Ajusta según corresponda, o déjalo null si no aplica

        solicitud.setEstado(false);  // Por defecto false (pendiente)
        solicitud.setMascotaID(mascota.mascotaID);
        solicitud.setUbicacionID(ubicacion.ubicacionID);

        // Llamar al servicio Retrofit para registrar
        SolicitudAdopcionServicios service = ClienteAPI.getRetrofit().create(SolicitudAdopcionServicios.class);

        Call<ResponseBody> call = service.registrarSolicitudAdopcion(solicitud);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Solicitud de adopción registrada correctamente", Toast.LENGTH_LONG).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(getContext(), "Error en el registro: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarCampos() {
        inputNombre.setText("");
        inputEspecie.setText("");
        inputRaza.setText("");
        inputAno.setText("");
        inputMes.setText("");
        inputSexo.setText("");
        inputTamano.setText("");
        inputDescripcion.setText("");
        inputLatitud.setText("");
        inputLongitud.setText("");
        inputCiudad.setText("");
        inputEstado.setText("");
        inputPais.setText("");
        imageViewPreview.setImageURI(null);
        imageViewPreview.setVisibility(View.GONE);
        imagenSeleccionada = null;
    }
}
