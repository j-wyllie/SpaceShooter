package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.text.ListFormatter;

public class Player extends Entity {
    private Bitmap _bitmap = null;
    int _health = 3;
    private final static int tagetHeight = 100; //Todo: remove as is a magic number
    private final static int STARTING_POS = 40;
    private final static float ACC = 1.1f;
    private final static float MIN_VEL = 1f;
    private final static float MAX_VEL = 10f;
    private final static float GRAVITY = 1.1f;
    private final static float LIFT = -2f;
    private final static float DRAG = 0.97f;

    Player() {
        _bitmap = BitmapFactory.decodeResource(
                _game.getContext().getResources(),
                R.drawable.player_ship
        );
        _bitmap = Utils.scaleToTargetHeight(_bitmap, tagetHeight);
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();
        _velX = 4;
        _x = STARTING_POS;

    }



    @Override
    void update() {
        _y += _velY;
        _velX *= DRAG;
        _velY += GRAVITY;
        if (_game._isBoosting) {
            _velX *= ACC;
            _velY += LIFT;
        }
        _velX = Utils.clamp(_velX, MIN_VEL, MAX_VEL);
        _velY = Utils.clamp(_velY, -MAX_VEL, MAX_VEL);
       // _x = Utils.wrap(_x, -_width, Game.STAGE_WIDTH);
        _y = Utils.clamp(_y, 0, Game.STAGE_HEIGHT - _height);

        _game._playerSpeed = _velX;
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
