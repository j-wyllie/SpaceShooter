package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements Runnable {
    public static final String TAG = "Game";
    public static final int STAGE_WIDTH = 1280;
    public static final int STAGE_HEIGHT = 720;
    static final int STAR_COUNT = 40;
    static final int ENEMY_COUNT = 6;

    private Thread _gameThread;
    private volatile boolean _isRunning = false;
    private SurfaceHolder _holder;
    private Paint _paint;
    private Canvas _canvas;

    private ArrayList<Entity> _entities = new ArrayList<>();
    private Player _player = null;
    Random _rng = new Random();
    private JukeBox _jukeBox = null;
    private Hud _hud = null;

    volatile boolean _isBoosting = false;
    float _playerSpeed = 0f;
    private boolean _gameOver = true;

    public Game(Context context) {
        super(context);
        Entity._game = this;
        _holder = getHolder();
        _holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        _paint = new Paint();
        _jukeBox = new JukeBox(context);
        _hud = new Hud(context);

        for (int i = 0; i < STAR_COUNT; i++) {
            _entities.add(new Star());
        }
        for (int i = 0; i < ENEMY_COUNT; i++) {
            _entities.add(new NormalEnemy());
        }
        _entities.add(new BossEnemy());
        _player = new Player();
    }

    private void restart() {
        for (Entity entity : _entities) {
            entity.respawn();
        }
        _player.respawn();
        _gameOver = false;
        _hud.restart();
        _jukeBox.play(GameEvent.LEVEL_START);
    }

    @Override
    public void run() {
        while (_isRunning) {
            update();
            render();
        }
    }

    private void update() {
        if (_gameOver) {
            return;
        }
        _player.update();
        for (Entity entity : _entities) {
            entity.update();
        }
        checkCollisions();
        checkGameOver();
        _hud.update(_playerSpeed);
    }

    private void checkGameOver() {
        if (_player._health <= 0) {
            _gameOver = true;
            _hud.gameOver();
            _jukeBox.play(GameEvent.DEATH);
        }
    }

    private void checkCollisions() {
        Entity temp = null;
        for (int i = STAR_COUNT; i < _entities.size(); i++) {
            temp = _entities.get(i);
            if (_player.isColliding(temp)) {
                _player.onCollision(temp);
                temp.onCollision(_player);
                _jukeBox.play(GameEvent.COLLISION);
            }
        }
    }

    private void render() {
        if (!acquireAndLockCanvas()) return;
        _canvas.drawColor(Color.BLACK);
        for (Entity entity : _entities) {
            entity.render(_canvas, _paint);
        }
        _player.render(_canvas, _paint);
        _hud.renderHUD(_canvas, _paint, _player._health, _gameOver);
        _holder.unlockCanvasAndPost(_canvas);
    }


    private boolean acquireAndLockCanvas() {
        if (!_holder.getSurface().isValid()) {
            return false;
        }

        _canvas = _holder.lockCanvas();

        return (_canvas != null);
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        _isRunning = true;
        _gameThread = new Thread(this);
        _gameThread.start();
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        _isRunning = false;
        try {
            _gameThread.join();
        } catch (InterruptedException e) {
            Log.d(TAG, Log.getStackTraceString(e.getCause()));
        }

    }

    public void onGameEvent(final GameEvent event, final Entity e /*can be null!*/) {
        _jukeBox.playSoundForGameEvent(event);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                _isBoosting = false;
                if (_gameOver) {
                    restart();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                _isBoosting = true;
                _jukeBox.play(GameEvent.BOOST);
                break;
        }
        return true;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        for (Entity entity : _entities) {
            entity.destroy();
        }

        _gameThread = null;
        Entity._game = null;
        _jukeBox.destroy();
    }

    public boolean getIsBoosting() {
        return this._isBoosting;
    }
}
