package com.example.adoptmemovil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adoptmemovil.modelo.Ubicacion;


public class RegistrarUsuarioActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Ubicacion ubicacion;
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etTelefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarusuario);

        // Inicialización de vistas
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etTelefono = findViewById(R.id.etTelefono);

        // Botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());

        Button btnRegistrarUbicacion = findViewById(R.id.btnRegistrarUbicacion);
        btnRegistrarUbicacion.setOnClickListener(view -> {
            solicitarPermisoUbicacion();
        });

        // Botón de registro
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

        if (!correo.contains("@") || !correo.endsWith(".com")) {
            mostrarError("Ingrese un correo válido (ejemplo@dominio.com)");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            mostrarError("Las contraseñas no coinciden");
            return false;
        }

        if (telefono.length() != 10 || !telefono.matches("\\d+")) {
            mostrarError("El teléfono debe tener 10 dígitos numéricos");
            return false;
        }

        return true;
    }

    private void registrarUsuario() {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        limpiarCampos();
        finish(); // Opcional: Regresar al login
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
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Toast.makeText(this, "Permiso de ubicación ya concedido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de ubicación concedido", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MapaRegistroActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
