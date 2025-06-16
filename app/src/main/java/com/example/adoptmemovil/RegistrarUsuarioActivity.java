package com.example.adoptmemovil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etTelefono;
    private EditText etCiudad;

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
        etCiudad = findViewById(R.id.etCiudad);

        // Botón de retroceso
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());

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
        String ciudad = etCiudad.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || telefono.isEmpty() || ciudad.isEmpty()) {
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
        etCiudad.getText().clear();
    }
}
