 package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.content.SharedPreferences;
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
    public static final String PREFS = "com.joshuawyllie.spaceshooter";
    public static final String LONGEST_DIST = "longest_distance";
    static final int STAGE_WIDTH = 1280;
    static final int STAGE_HEIGHT = 720 ;
    static final int STAR_COUNT = 40;
    static final int ENEMY_COUNT = 8;
    static final float HUD_SIZE = 48;

    private Thread _gameThread;
    private volatile boolean _isRunning = false;
    private SurfaceHolder _holder;
    private Paint _paint;
    private Canvas _canvas;

    private ArrayList<Entity> _entities = new ArrayList<>();
    private Player _player = null;
    Random _rng = new Random();
    private JukeBox _jukeBox = null;
    private SharedPreferences _prefs = null;
    private SharedPreferences.Editor _editor = null;

    volatile boolean _isBoosting = false;
    float _playerSpeed = 0f;
    int _distanceTraveled = 0;
    int _maxDistanceTraveled = 0;
    private boolean _gameOver = true;

    public Game(Context context) {
        super(context);
        Entity._game = this;
        _holder = getHolder();
        _holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        _paint = new Paint();
        _jukeBox = new JukeBox(context);

        _prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        _editor = _prefs.edit();

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
        _distanceTraveled = 0;
        _maxDistanceTraveled = _prefs.getInt(LONGEST_DIST, 0);
    }

    @Override
    public void run() {
        while (_isRunning) {
            update();
            render();
        }
    }

    private void update() {
        if (_gameOver) { return; }

        _player.update();
        for (Entity entity : _entities) {
            entity.update();
        }
        checkCollisions();
        checkGameOver();
        _distanceTraveled += _playerSpeed;
    }

    private void checkGameOver() {
        if (_player._health <= 0) {
            _gameOver = true;
            if (_distanceTraveled > _maxDistanceTraveled) {
                _maxDistanceTraveled = _distanceTraveled;
                _editor.putInt(LONGEST_DIST, _maxDistanceTraveled);
                _editor.apply();
            }
        }
    }

    private void checkCollisions() {
        Entity temp = null;
        for (int i = STAR_COUNT; i < _entities.size(); i++) {
            temp = _entities.get(i);
            if (_player.isColliding(temp)) {
                _player.onCollision(temp);
                temp.onCollision(_player);
                _jukeBox.play(JukeBox.CRASH);
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
        renderHUD(_canvas, _paint);
        _holder.unlockCanvasAndPost(_canvas);
    }

    private void renderHUD(final Canvas canvas, final Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(HUD_SIZE);
        final float centerY = STAGE_HEIGHT / 2;
        if (_gameOver) {
            canvas.drawText(getResources().getString(R.string.game_over), centerY, HUD_SIZE, paint);
            canvas.drawText(getResources().getString(R.string.game_start_instructions), 10, centerY + HUD_SIZE, paint);
        } else {
            canvas.drawText(String.format("%s%d", getResources().getString(R.string.health), _player._health), 10, centerY, paint);
            canvas.drawText(String.format("%s%d", getResources().getString(R.string.distance_traveled), _distanceTraveled), 10, centerY + HUD_SIZE, paint);
        }

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

    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        for (Entity entity : _entities) {
            entity.destroy();
        }

        _gameThread = null;
        Entity._game = null;
        _jukeBox.destroy();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                _isBoosting = false;
                if (_gameOver) {
                    restart();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                _isBoosting = true;
                break;
        }
        return true;
    }
}
