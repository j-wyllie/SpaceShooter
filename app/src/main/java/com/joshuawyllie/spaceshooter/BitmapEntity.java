package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BitmapEntity extends Entity {
    protected Bitmap _bitmap = null;
    protected Bitmap _bitmap_alt = null;

    protected void loadBitmap(int resID, int targetHeight) {
        destroy();
        _bitmap = BitmapFactory.decodeResource(
                _game.getContext().getResources(),
                resID
        );
        _bitmap = Utils.scaleToTargetHeight(_bitmap, targetHeight);
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();
        _velX = 4;
    }

    protected void loadBitmap(int resID, int resIDalt, int targetHeight) {
        destroy();
        _bitmap = BitmapFactory.decodeResource(
                _game.getContext().getResources(),
                resID
        );
        _bitmap = Utils.scaleToTargetHeight(_bitmap, targetHeight);
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();

        _bitmap_alt = BitmapFactory.decodeResource(
                _game.getContext().getResources(),
                resIDalt
        );
        _bitmap_alt = Utils.scaleToTargetHeight(_bitmap_alt, targetHeight);
        _velX = 4;
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(_bitmap, _x, _y, paint);
    }

    @Override
    void destroy() {
        if (_bitmap != null) {
            _bitmap.recycle();
            _bitmap = null;
        }
        if (_bitmap_alt != null) {
            _bitmap_alt.recycle();
            _bitmap_alt = null;
        }
    }
}
