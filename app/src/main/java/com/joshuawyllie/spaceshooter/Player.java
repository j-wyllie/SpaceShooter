package com.joshuawyllie.spaceshooter;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends BitmapEntity {

    private final static int tagetHeight = 100;
    private final static int STARTING_POS = 40;
    private final static int STARTING_HEALTH = 3;
    private final static float ACC = 1.1f;
    private final static float MIN_VEL = 1f;
    private final static float MAX_VEL = 10f;
    private final static float GRAVITY = 1.1f;
    private final static float LIFT = -2f;
    private final static float DRAG = 0.97f;
    private final static int NUM_RECOVERY_FRAMES = 5;

    int _health = STARTING_HEALTH;
    private boolean _recovery = false;
    private int _framesPast = 0;

    Player() {
        super();
        loadBitmap(R.drawable.player_ship, R.drawable.player_ship_boost, tagetHeight);
        respawn();
    }

    @Override
    void respawn() {
        super.respawn();
        _health = STARTING_HEALTH;
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
        _y = Utils.clamp(_y, 0, Game.STAGE_HEIGHT - _height);

        _game._playerSpeed = _velX;

        _framesPast++;
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        if (_game.getIsBoosting()) {
            canvas.drawBitmap(_bitmap_alt, _x, _y, paint);
        } else {
            canvas.drawBitmap(_bitmap, _x, _y, paint);
        }
    }

    @Override
    void onCollision(Entity that) {
        if (_framesPast > NUM_RECOVERY_FRAMES) {
            _recovery = false;
            _framesPast = 0;
        }

        if (!_recovery) {
            _recovery = true;
            _health--;
            _framesPast = 0;
        }
    }

}
