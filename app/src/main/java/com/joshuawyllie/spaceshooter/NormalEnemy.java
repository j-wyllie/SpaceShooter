package com.joshuawyllie.spaceshooter;

public class NormalEnemy extends Enemy {

    private final static int ENEMY_HEIGHT = 50;
    private final static int NUM_ENEMIES = 3;

    NormalEnemy() {
        super(ENEMY_HEIGHT);
        loadRandomEnemyBitmap();
        _bitmap = Utils.flipBitmap(_bitmap, false);
    }

    private void loadRandomEnemyBitmap() {
        int randomEnemy = _game._rng.nextInt(NUM_ENEMIES + 1);
        int resID = R.drawable.player_ship;
        switch (randomEnemy) {
            case(0):
                resID = R.drawable.enemy_0;
                break;
            case(1):
                resID = R.drawable.enemy_1;
                break;
            case(2):
                resID = R.drawable.enemy_2;
                break;
        }
        loadBitmap(resID, ENEMY_HEIGHT);
    }
}
