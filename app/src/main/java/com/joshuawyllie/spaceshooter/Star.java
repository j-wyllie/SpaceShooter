package com.joshuawyllie.spaceshooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Star extends Entity {

    private final static int COLOUR = 0xFFFF77FF;
    private final static int MAX_RADIUS = 8;
    private final static int MIN_RADIUS = 2;
    private final static float INIT_VEL = 4f;

    private int _radius;
    private float _distanceMultiplier;
    private int _colour;

    Star() {
        _x = _game._rng.nextInt(Game.STAGE_WIDTH);
        _y = _game._rng.nextInt(Game.STAGE_HEIGHT);
        _radius = _game._rng.nextInt(MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
        _width = _radius * 2;
        _height = _radius * 2;
        _velX = INIT_VEL;
        _distanceMultiplier = getDistMult(_radius);
        _colour = getInitColour();
    }

    private float getDistMult(int _radius) {
        return (float) _radius / MAX_RADIUS;
    }

    private int getInitColour() {
        return Color.argb(255, _game._rng.nextInt(256), _game._rng.nextInt(128), _game._rng.nextInt(256));
    }


    @Override
    void update() {
        _velX = -_game._playerSpeed * _distanceMultiplier;
        _x += _velX;
        _x = Utils.wrap(_x, 0, Game.STAGE_WIDTH + _width);
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        paint.setColor(_colour);
        canvas.drawCircle(_x + _radius, _y, _radius, paint);
    }
}
