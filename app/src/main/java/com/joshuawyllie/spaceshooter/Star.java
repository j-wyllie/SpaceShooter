package com.joshuawyllie.spaceshooter;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Star extends Entity {
    private static int _colour = 0xFFFF77FF;
    private int _radius ;

    Star() {
        _x = _game._rng.nextInt(Game.STAGE_WIDTH);
        _y = _game._rng.nextInt(Game.STAGE_HEIGHT);
        _radius = _game._rng.nextInt(6) + 2;
        _width = _radius * 2;
        _height = _radius * 2;
        _velX = -4f;    // ToDo: DO NOT USE MAGIC NUMBERS, FOLLOW THE CODE STANDARD OR YOU FAIL!!!
    }

    @Override
    void update() {
        super.update();
        if (right() < 0) {
            setLeft(Game.STAGE_WIDTH);
        }
        if (bottom() < 0) {
            setTop(Game.STAGE_HEIGHT);
        }
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        paint.setColor(_colour);
        canvas.drawCircle(_x + _radius, _y, _radius, paint);
    }
}
