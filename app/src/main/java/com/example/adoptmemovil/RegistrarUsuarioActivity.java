package com.example.adoptmemovil;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptmemovil.modelo.Ubicacion;
import com.example.adoptmemovil.utilidades.Validador;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class RegistrarUsuarioActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;

    private Ubicacion ubicacionFinal;
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etTelefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarusuario);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etTelefono = findViewById(R.id.etTelefono);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());

        Button btnRegistrarUbicacion = findViewById(R.id.btnRegistrarUbicacion);
        btnRegistrarUbicacion.setOnClickListener(view -> {
            solicitarPermisoUbicacion();
        });

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(view -> {
            if (validarCampos()) {
                registrarUsuario();
            }
        });
    }

    private boolean validarCampos() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String telefono = etTelefono.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || telefono.isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return false;
        }

        if (!Validador.validarNombre(nombre)) {
            mostrarError("Ingrese un nombre válido");
            return false;
        }

        if (!Validador.validarCorreo(correo)) {
            mostrarError("Ingrese un correo válido (ejemplo@dominio.com)");
            return false;
        }

        if (!Validador.validarContrasena(nombre)) {
            mostrarError("Ingrese una contraseña válida");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            mostrarError("Las contraseñas no coinciden");
            return false;
        }

        if (!Validador.validarTelefono(telefono)) {
            mostrarError("El teléfono debe tener 10 dígitos numéricos");
            return false;
        }

        return true;
    }

    private void registrarUsuario() {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        limpiarCampos();
        finish();
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void limpiarCampos() {
        etNombre.getText().clear();
        etCorreo.getText().clear();
        etPassword.getText().clear();
        etConfirmPassword.getText().clear();
        etTelefono.getText().clear();
    }

    private void solicitarPermisoUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            boolean fineLocationGranted = false;
            boolean coarseLocationGranted = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    fineLocationGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
                if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    coarseLocationGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
            }

            if (fineLocationGranted || coarseLocationGranted) {
                obtenerUbicacionYContinuar();
            } else {
                Toast.makeText(this, "Permisos de ubicación denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void obtenerUbicacionYContinuar() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitud = location.getLatitude();
                        double longitud = location.getLongitude();

                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> direcciones = geocoder.getFromLocation(latitud, longitud, 1);
                            assert direcciones != null;
                            if (!direcciones.isEmpty()) {
                                Intent intent = crearIntentMapaRegistro(direcciones, latitud, longitud);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            Toast.makeText(this, "No se pudo obtener dirección", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @NonNull
    private Intent crearIntentMapaRegistro(List<Address> direcciones, double latitud, double longitud) {
        Address direccion = direcciones.get(0);
        String ciudad = direccion.getLocality();
        String estado = direccion.getAdminArea();
        String pais = direccion.getCountryName();

        Intent intent = new Intent(this, MapaRegistroActivity.class);
        intent.putExtra("latitud", latitud);
        intent.putExtra("longitud", longitud);
        intent.putExtra("ciudad", ciudad);
        intent.putExtra("estado", estado);
        intent.putExtra("pais", pais);
        return intent;
    }

}
