package com.example.adoptmemovil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.adoptmemovil.modelo.Ubicacion;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;

public class MapaRegistroActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_registro);
        Toast.makeText(this, "Llegó al setContentView", Toast.LENGTH_SHORT).show();

        File osmDir = new File(getFilesDir(), "osmdroid");
        File tileCache = new File(osmDir, "tiles");
        Configuration.getInstance().setOsmdroidBasePath(osmDir);
        Configuration.getInstance().setOsmdroidTileCache(tileCache);

        Configuration.getInstance().load(
                getApplicationContext(),
                getSharedPreferences("osmdroid", 0)
        );

        mapView = findViewById(R.id.mapaRegistro);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        // Obtener datos del intent
        double latitud = getIntent().getDoubleExtra("latitud", 0.0);
        double longitud = getIntent().getDoubleExtra("longitud", 0.0);
        String ciudad = getIntent().getStringExtra("ciudad");
        String estado = getIntent().getStringExtra("estado");
        String pais = getIntent().getStringExtra("pais");

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.latitud = latitud;
        ubicacion.longitud = longitud;
        ubicacion.ciudad = ciudad;
        ubicacion.estado = estado;
        ubicacion.pais = pais;

        inicializarMapaConUbicacion(ubicacion);
    }

    private void inicializarMapaConUbicacion(Ubicacion ubicacion) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            GeoPoint punto = new GeoPoint(ubicacion.latitud, ubicacion.longitud);
            mapView.getController().setZoom(15.0);
            mapView.getController().setCenter(punto);

            Marker marcador = new Marker(mapView);
            marcador.setPosition(punto);
            marcador.setTitle("Tu ubicación");
            mapView.getOverlays().add(marcador);

            Toast.makeText(this, "Se creó el mapa de registro", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
