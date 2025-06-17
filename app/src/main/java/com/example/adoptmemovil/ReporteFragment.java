package com.example.adoptmemovil;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReporteFragment extends Fragment {

    private BarChart barChart;
    private Button btnGuardar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reporte, container, false);

        barChart = view.findViewById(R.id.barChart);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        setupBarChart();

        btnGuardar.setOnClickListener(v -> {
            saveChartToGallery();
        });

        return view;
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5));
        entries.add(new BarEntry(1, 10));
        entries.add(new BarEntry(2, 7));
        entries.add(new BarEntry(3, 12));
        entries.add(new BarEntry(4, 1));
        entries.add(new BarEntry(5, 16));
        entries.add(new BarEntry(6, 9));
        entries.add(new BarEntry(7, 5));
        entries.add(new BarEntry(8, 4));
        entries.add(new BarEntry(9, 13));
        entries.add(new BarEntry(10, 10));
        entries.add(new BarEntry(11, 8));

        BarDataSet dataSet = new BarDataSet(entries, "Mascotas adoptadas");
        dataSet.setColor(Color.BLUE);
        BarData data = new BarData(dataSet);

        barChart.setData(data);

        String[] meses = new String[]{"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(meses));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }

    private void saveChartToGallery() {
        Bitmap bitmap = barChart.getChartBitmap();
        if (bitmap == null) {
            Toast.makeText(requireContext(), "Error: El gráfico está vacío", Toast.LENGTH_LONG).show();
            return;
        }


        ContentValues values = new ContentValues();
        String fileName = "grafica_adopciones_" + System.currentTimeMillis() + ".png";
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/AdoptMe");

        ContentResolver resolver = requireContext().getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (uri == null) {
            Toast.makeText(requireContext(), "Error al crear archivo de imagen", Toast.LENGTH_LONG).show();
            return;
        }

        try (OutputStream out = resolver.openOutputStream(uri)) {
            if (out == null) {
                Toast.makeText(requireContext(), "Error al abrir flujo de salida", Toast.LENGTH_LONG).show();
                return;
            }

            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            if (success) {
                Toast.makeText(requireContext(), "Imagen guardada en Galería", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Error al comprimir imagen", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "IOException: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
