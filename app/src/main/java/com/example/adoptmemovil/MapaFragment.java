package com.example.adoptmemovil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.File;

public class MapaFragment extends Fragment {

    private MapView mapView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mapa, container, false);

        File osmDir = new File(requireContext().getFilesDir(), "osmdroid");
        File tileCache = new File(osmDir, "tiles");
        Configuration.getInstance().setOsmdroidBasePath(osmDir);
        Configuration.getInstance().setOsmdroidTileCache(tileCache);

        Configuration.getInstance().load(
                requireContext().getApplicationContext(),
                requireContext().getSharedPreferences("osmdroid", 0)
        );

        mapView = root.findViewById(R.id.mapaPrincipal);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(23.6345,  -102.5528);
        mapView.getController().setZoom(6.0);
        mapView.getController().setCenter(startPoint);

        CompassOverlay compassOverlay = new CompassOverlay(requireContext(), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.getOverlays().add(rotationGestureOverlay);

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setAlignBottom(true);
        mapView.getOverlays().add(scaleBarOverlay);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}