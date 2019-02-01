package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends Entity {
    private Bitmap _bitmap = null;
    int _health = 3;
    final static int tagetHeight = 100; //Todo: remove as is a magic number

    Player() {
        _bitmap = BitmapFactory.decodeResource(
                _game.getContext().getResources(),
                R.drawable.player_ship
        );
        _bitmap = Utils.scaleToTargetHeight(_bitmap, tagetHeight);
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();
        _velX = 1;

    }



    @Override
    void update() {
        super.update();
        _x = Utils.wrap(_x, -_width, Game.STAGE_WIDTH);
        _y = Utils.wrap(_y, -_height, Game.STAGE_HEIGHT);
        if (left() > Game.STAGE_WIDTH) {
            setRight(0);
        }
        if (top() > Game.STAGE_HEIGHT) {
            setLeft(0);
        }
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(_bitmap, _x, _y, paint);
    }

    @Override
    void onCollision(Entity that) {
        super.onCollision(that);
    }

    @Override
    void destroy() {
        if (_bitmap != null) {
            _bitmap.recycle();
            _bitmap = null;
        }
    }
}
