package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public abstract class Utils {

    static Matrix _matrix = new Matrix();

    public static Bitmap scaleToTargetHeight(Bitmap bitmapSrc, int newH) {
        float ratio = newH / (float) bitmapSrc.getHeight();
        int newW = (int) (bitmapSrc.getWidth() * ratio);
        bitmapSrc = Bitmap.createScaledBitmap(bitmapSrc, newW, newH, true);     //note: scaling can be done upon loading
        return bitmapSrc;
    }

    public static Bitmap flipBitmap(Bitmap src, final boolean horizontal) {
        _matrix.reset();
        final int cx = src.getWidth() / 2;
        final int cy = src.getHeight() / 2;
        if (horizontal) {
            _matrix.postScale(1, -1, cx, cy);
        } else {
            _matrix.postScale(-1, 1, cx, cy);
        }
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), _matrix, true);

    }

    public static float wrap(float value, final float min, final float max) {
        if (value < min) {
            value = max;
        } else if (value > max) {
            value = min;
        }
        return value;
    }

    public static float clamp(float value, final float min, final float max) {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }
        return value;
    }
}
