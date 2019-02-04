package com.joshuawyllie.spaceshooter;

import android.graphics.Bitmap;

public class Player extends BitmapEntity {
    private Bitmap _bitmap = null;
    int _health = 3;
    private final static int tagetHeight = 100; //Todo: remove as is a magic number
    private final static int STARTING_POS = 40;
    private final static int STARTING_HEALTH = 3;
    private final static float ACC = 1.1f;
    private final static float MIN_VEL = 1f;
    private final static float MAX_VEL = 10f;
    private final static float GRAVITY = 1.1f;
    private final static float LIFT = -2f;
    private final static float DRAG = 0.97f;

    Player() {
        super();
        loadBitmap(R.drawable.player_ship, tagetHeight);
        respawn();
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
    }


    @Override
    void onCollision(Entity that) {
        // TODO: implement recovery frames (temporary immortality after taking damage)
        _health--;
    }


}
