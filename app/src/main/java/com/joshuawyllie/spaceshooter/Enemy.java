package com.joshuawyllie.spaceshooter;

public class Enemy extends BitmapEntity {
    private final static int ENEMY_HEIGHT = 80;
    private final static int ENEMY_SPAWN_OFFSET = Game.STAGE_WIDTH;

    Enemy() {
        super();
        _x = Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = _game._rng.nextInt(Game.STAGE_HEIGHT - ENEMY_HEIGHT);
        int resID = R.drawable.player_ship;
        loadBitmap(resID, ENEMY_HEIGHT);
        _bitmap = Utils.flipBitmap(_bitmap, false);
        // ToDo: gen random int and switch over it setting random enemy accordingly
    }

    @Override
    void update() {
        _velX = -_game._playerSpeed;
        _x += _velX;
        if (right() < 0) {
            _x = Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        }
    }
}
