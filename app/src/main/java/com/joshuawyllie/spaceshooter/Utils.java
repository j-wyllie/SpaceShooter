package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;

public abstract class Utils {
    public static Bitmap scaleToTargetHeight(Bitmap bitmapSrc, int newH) {
        float ratio = newH / (float) bitmapSrc.getHeight();
        int newW = (int) (bitmapSrc.getWidth() * ratio);
        bitmapSrc = Bitmap.createScaledBitmap(bitmapSrc, newW, newH, true);     //note: scaling can be done upon loading

        return bitmapSrc;
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
