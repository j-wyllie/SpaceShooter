package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BitmapEntity extends Entity {
    protected Bitmap _bitmap = null;

    BitmapEntity() {


    }

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
    }
}
