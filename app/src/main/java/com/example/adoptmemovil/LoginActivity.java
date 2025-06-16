package com.example.adoptmemovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Botón de Iniciar Sesión
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNombre = findViewById(R.id.etNombre);
                EditText etPassword = findViewById(R.id.etPassword);

                String nombre = etNombre.getText().toString();
                String password = etPassword.getText().toString();

                if (nombre.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "¡Completa todos los campos!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    if (nombre.equalsIgnoreCase("admin")) {
                        // Ir a MenuAdminActivity si el usuario es admin
                        Intent intent = new Intent(LoginActivity.this, MenuAdminActivity.class);
                        startActivity(intent);
                    } else {
                        // Ir a MenuActivity para usuarios normales
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ir a RegistrarUsuarioActivity
                Intent intent = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }
}
