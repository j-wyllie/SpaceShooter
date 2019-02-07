package com.joshuawyllie.spaceshooter;

public abstract class Enemy extends BitmapEntity {

    final static int ENEMY_SPAWN_OFFSET = Game.STAGE_WIDTH;
    static int ENEMY_HEIGHT = 50;


    Enemy(int enemyHeight) {
        ENEMY_HEIGHT = enemyHeight;
        _x = getRandomX();
        _y = getRandomY();
        respawn();
    }

    @Override
    void respawn() {
        _x = getRandomX();
        _y = getRandomY();
        _velX = 0f;
        _velY = 0f;
    }


    @Override
    void update() {
        _velX = -_game._playerSpeed;
        _x += _velX;
        if (right() < 0) {
            _x = getRandomX();
        }
    }

    @Override
    void onCollision(Entity that) {
        _x = getRandomX();
    }

    private int getRandomX() {
        return Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
    }

    private int getRandomY() {
        return _game._rng.nextInt(Game.STAGE_HEIGHT - ENEMY_HEIGHT);
    }
}
