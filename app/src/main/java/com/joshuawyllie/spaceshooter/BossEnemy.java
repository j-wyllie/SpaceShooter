package com.joshuawyllie.spaceshooter;

public class BossEnemy extends Enemy {

    private final static int ENEMY_HEIGHT = 100;
    private final static float MOVE_SPEED = 2f;
    private boolean movingUp = false;

    BossEnemy() {
        super(ENEMY_HEIGHT);
        loadBitmap(R.drawable.enemy_3, ENEMY_HEIGHT);
        _bitmap = Utils.flipBitmap(_bitmap, false);
    }

    @Override
    void update() {
        super.update();
        if (_y > Game.STAGE_HEIGHT - ENEMY_HEIGHT) {
            movingUp = false;
        } else if (_y < 0) {
            movingUp = true;
        }
        if (movingUp) {
            _y += MOVE_SPEED;
        } else {
            _y -= MOVE_SPEED;
        }
    }
}
