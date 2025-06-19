package com.example.adoptmemovil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.adoptmemovil.MapaRegistroActivity;
import com.example.adoptmemovil.R;
import com.example.adoptmemovil.modelo.Mascota;
import com.example.adoptmemovil.modelo.SolicitudAdopcion;
import com.example.adoptmemovil.modelo.Ubicacion;
import com.example.adoptmemovil.servicios.ClienteAPI;
import com.example.adoptmemovil.servicios.SolicitudAdopcionServicios;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarAdopcionFragment extends Fragment {

    private static final int REQUEST_LOCATION_PERMISSIONS = 100;
    private EditText inputNombre, inputEspecie, inputRaza, inputAno, inputMes, inputSexo, inputTamano, inputDescripcion;
    private Button btnElegirUbicacion, btnSubirFoto, btnSubirVideo, btnRegistrar;
    private ImageView imageViewPreview;
    private Uri imagenSeleccionada;
    private Uri videoSeleccionado;
    private TextView txtNombreVideo;
    private Ubicacion ubicacionFinal;
    private ActivityResultLauncher<Intent> launcherGaleria;
    private ActivityResultLauncher<Intent> launcherMapa;
    private ActivityResultLauncher<Intent> launcherVideo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_adopcion, container, false);

        // Inputs
        inputNombre = view.findViewById(R.id.input_nombre);
        inputEspecie = view.findViewById(R.id.input_especie);
        inputRaza = view.findViewById(R.id.input_raza);
        inputAno = view.findViewById(R.id.input_ano);
        inputMes = view.findViewById(R.id.input_mes);
        inputSexo = view.findViewById(R.id.input_sexo);
        inputTamano = view.findViewById(R.id.input_tamano);
        inputDescripcion = view.findViewById(R.id.input_descripcion);

        // Botones
        btnElegirUbicacion = view.findViewById(R.id.btn_elegir_ubicacion);
        btnSubirFoto = view.findViewById(R.id.btn_subir_foto);
        btnSubirVideo = view.findViewById(R.id.btn_subir_video);
        btnRegistrar = view.findViewById(R.id.btn_registrar);
        imageViewPreview = view.findViewById(R.id.imageViewPreview);
        txtNombreVideo = view.findViewById(R.id.txt_nombre_video);

        // Lanzadores
        launcherGaleria = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imagenSeleccionada = result.getData().getData();
                        imageViewPreview.setImageURI(imagenSeleccionada);
                    }
                });

        launcherMapa = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        ubicacionFinal = new Ubicacion();
                        ubicacionFinal.setLatitud(data.getDoubleExtra("latitud", 0.0));
                        ubicacionFinal.setLongitud(data.getDoubleExtra("longitud", 0.0));
                        ubicacionFinal.setCiudad(data.getStringExtra("ciudad"));
                        ubicacionFinal.setEstado(data.getStringExtra("estado"));
                        ubicacionFinal.setPais(data.getStringExtra("pais"));
                        Toast.makeText(getContext(), "Ubicación registrada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Registro de ubicación cancelado", Toast.LENGTH_SHORT).show();
                    }
                });

        launcherVideo = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        videoSeleccionado = result.getData().getData();
                        String nombre = obtenerNombreArchivo(videoSeleccionado);
                        txtNombreVideo.setText(nombre != null ? nombre : "Video seleccionado");
                    }
                });

        // Acciones
        btnSubirFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            launcherGaleria.launch(intent);
        });

        btnSubirVideo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");
            launcherVideo.launch(intent);
        });

        btnElegirUbicacion.setOnClickListener(v -> verificarYPedirPermisoUbicacion());

        btnRegistrar.setOnClickListener(v -> registrarAdopcion());

        return view;
    }

    private String obtenerNombreArchivo(Uri uri) {
        if (uri == null) return null;
        String path = uri.getPath();
        if (path == null) return null;
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }

    private void verificarYPedirPermisoUbicacion() {
        boolean fine = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarse = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (fine || coarse) {
            obtenerUbicacionDisponible();
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_LOCATION_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            for (int res : grantResults) {
                if (res == PackageManager.PERMISSION_GRANTED) {
                    obtenerUbicacionDisponible();
                    return;
                }
            }
            Toast.makeText(getContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerUbicacionDisponible() {
        FusedLocationProviderClient fused = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;

        fused.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                try {
                    List<Address> direcciones = geocoder.getFromLocation(lat, lon, 1);
                    if (!direcciones.isEmpty()) {
                        Address direccion = direcciones.get(0);
                        Intent intent = new Intent(requireContext(), MapaRegistroActivity.class);
                        intent.putExtra("latitud", lat);
                        intent.putExtra("longitud", lon);
                        intent.putExtra("ciudad", direccion.getLocality());
                        intent.putExtra("estado", direccion.getAdminArea());
                        intent.putExtra("pais", direccion.getCountryName());
                        launcherMapa.launch(intent);
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "No se pudo obtener dirección", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        });
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
                || TextUtils.isEmpty(ano) || TextUtils.isEmpty(mes) || TextUtils.isEmpty(sexo)
                || TextUtils.isEmpty(tamano) || TextUtils.isEmpty(descripcion)) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imagenSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ubicacionFinal == null) {
            Toast.makeText(getContext(), "Por favor selecciona una ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        String edad = ano + " años " + mes + " meses";

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setEdad(edad);
        mascota.setSexo(sexo);
        mascota.setTamaño(tamano);
        mascota.setDescripcion(descripcion);

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setPublicadorID(1);
        solicitud.setAdoptanteID(2);
        solicitud.setEstado(false);
        solicitud.setMascota(mascota);
        solicitud.setUbicacion(ubicacionFinal);
        // Video seleccionado (opcional) aún no se usa, pero puedes anexarlo aquí si decides enviarlo.

        SolicitudAdopcionServicios service = ClienteAPI.getRetrofit().create(SolicitudAdopcionServicios.class);
        Call<ResponseBody> call = service.registrarSolicitudAdopcion(solicitud);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    MapaFragment mapaFragment = new MapaFragment();

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, mapaFragment)
                            .commit();

                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.e("Error 400", error);
                        Toast.makeText(getContext(), "Error en el registro: " + error, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

