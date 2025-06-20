package com.example.adoptmemovil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.adoptmemovil.modelo.Ubicacion;
import com.example.adoptmemovil.utilidades.InterfazUsuarioUtils;
import com.example.adoptmemovil.utilidades.UsuarioSingleton;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.adoptmemovil.gRPC.ServicioUbicacionGrpcCliente;
import ubicacion.SolicitudCercana;
import ubicacion.SolicitudesCercanas;

public class MapaFragment extends Fragment {

    private MapView mapView;
    private List<Marker> marcadoresSolicitudes = new ArrayList<>();
    private Marker marcadorUsuario = null;
    private ServicioUbicacionGrpcCliente servicioGrpc;

    private static final double ZOOM_VISIBLE = 10.0;
    private static final GeoPoint POSICION_DEFECTO_MEXICO = new GeoPoint(23.6345, -102.5528);

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

        mapView.getController().setZoom(6.0);
        mapView.getController().setCenter(POSICION_DEFECTO_MEXICO);

        CompassOverlay compassOverlay = new CompassOverlay(requireContext(), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.getOverlays().add(rotationGestureOverlay);

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setAlignBottom(true);
        mapView.getOverlays().add(scaleBarOverlay);

        servicioGrpc = new ServicioUbicacionGrpcCliente();

        Ubicacion ubicacionUsuario = UsuarioSingleton.getInstancia().getUsuarioActual().getUbicacion();

        if (ubicacionUsuario != null) {
            obtenerYMostrarUbicacionUsuario(ubicacionUsuario);
        }

        return root;
    }

    private void obtenerYMostrarUbicacionUsuario(Ubicacion ubicacionUsuario) {
        double latitud = ubicacionUsuario.getLatitud();
        double longitud = ubicacionUsuario.getLongitud();

        GeoPoint ubicacion = new GeoPoint(latitud, longitud);
        agregarMarcadorUsuario(ubicacion);
        mapView.getController().setZoom(17.0);
        mapView.getController().setCenter(ubicacion);

        obtenerUsuariosCercanos(latitud, longitud);
    }

    private void agregarMarcadorUsuario(GeoPoint ubicacion) {
        if (marcadorUsuario != null) {
            mapView.getOverlays().remove(marcadorUsuario);
        }

        Marker marker = new Marker(mapView);
        marker.setPosition(ubicacion);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        Drawable original = ContextCompat.getDrawable(requireContext(), R.drawable.ic_ubicacion);
        Drawable escalado = InterfazUsuarioUtils.escalarDrawable(requireContext(), original, 0.5f);
        marker.setIcon(escalado);

        marker.setTitle("Tu ubicación");

        mapView.getOverlays().add(marker);
        marcadorUsuario = marker;
    }


    private void agregarMarcadorSolicitud(GeoPoint ubicacion) {
        Marker marker = new Marker(mapView);
        marker.setPosition(ubicacion);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_ubicacion));
        marker.setTitle("Solicitud cercana");

        mapView.getOverlays().add(marker);
        marcadoresSolicitudes.add(marker);
    }

    private void limpiarMarcadoresSolicitudes() {
        for (Marker marcador : marcadoresSolicitudes) {
            mapView.getOverlays().remove(marcador);
        }
        marcadoresSolicitudes.clear();
    }

    private void obtenerUsuariosCercanos(double latitud, double longitud) {
        if (servicioGrpc == null) return;

        ubicacion.Ubicacion request = ubicacion.Ubicacion.newBuilder()
                .setUsuarioId(UsuarioSingleton.getInstancia().getUsuarioActual().getUsuarioID())
                .setLatitud(latitud)
                .setLongitud(longitud)
                .build();

        com.google.common.util.concurrent.ListenableFuture<SolicitudesCercanas> future =
                servicioGrpc.getStub().obtenerSolicitudesCercanas(request);

        future.addListener(() -> {
            try {
                SolicitudesCercanas respuesta = future.get();

                requireActivity().runOnUiThread(() -> {
                    limpiarMarcadoresSolicitudes();
                    for (SolicitudCercana sc : respuesta.getResultadosList()) {
                        GeoPoint punto = new GeoPoint(sc.getLatitud(), sc.getLongitud());
                        agregarMarcadorSolicitud(punto);
                    }
                    mapView.invalidate();
                });

            } catch (java.util.concurrent.ExecutionException ex) {
                Throwable causa = ex.getCause();
                if (causa instanceof io.grpc.StatusRuntimeException) {
                    Log.e("gRPC", "Error gRPC: " + ((io.grpc.StatusRuntimeException) causa).getStatus());
                    Toast.makeText(requireContext(),""+((io.grpc.StatusRuntimeException) causa).getStatus(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("gRPC", "Error inesperado: ", causa);
                    Toast.makeText(requireContext(),causa +"", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Log.e("gRPC", "Interrumpido", ex);
                Toast.makeText(requireContext(),""+ex, Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e("gRPC", "Error general", ex);
                Toast.makeText(requireContext(),""+ex, Toast.LENGTH_SHORT).show();
            }
        }, servicioGrpc.getExecutor());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (servicioGrpc != null) {
            try {
                servicioGrpc.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
