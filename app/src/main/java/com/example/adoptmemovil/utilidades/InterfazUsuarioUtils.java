package com.example.adoptmemovil.utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class InterfazUsuarioUtils {

    public static Drawable escalarDrawable(Context context, Drawable drawable, float escala) {
        if (drawable == null) return null;

        Bitmap originalBitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(originalBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        int nuevoAncho = (int) (canvas.getWidth() * escala);
        int nuevoAlto = (int) (canvas.getHeight() * escala);

        Bitmap escalado = Bitmap.createScaledBitmap(originalBitmap, nuevoAncho, nuevoAlto, true);
        return new BitmapDrawable(context.getResources(), escalado);
    }
}
