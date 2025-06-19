package com.example.adoptmemovil;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.adoptmemovil.utilidades.UsuarioSingleton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Cargar fragmento inicial
        loadFragment(new MapaFragment());

        // Configurar botones
        findViewById(R.id.btn_home).setOnClickListener(v -> loadFragment(new MapaFragment()));
        findViewById(R.id.btn_registrarMascota).setOnClickListener(v -> loadFragment(new RegistrarAdopcionFragment()));
        findViewById(R.id.btn_mascotas).setOnClickListener(v -> loadFragment(new AdopcionesRegistradasFragment()));
        //findViewById(R.id.btn_messages).setOnClickListener(v -> loadFragment(new MessagesFragment()));
        findViewById(R.id.btn_perfil).setOnClickListener(v -> loadFragment(new ConsultarUsuarioFragment()));

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(intent);
            UsuarioSingleton.getInstancia().cerrarSesion();
            finish();
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

