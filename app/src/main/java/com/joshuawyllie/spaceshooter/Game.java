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
    static final int STAGE_WIDTH = 1280;
    static final int STAGE_HEIGHT = 720 ;
    static final int STAR_COUNT = 40;
    static final int ENEMY_COUNT = 8;

    private Thread _gameThread;
    private volatile boolean _isRunning = false;
    private SurfaceHolder _holder;
    private Paint _paint;
    private Canvas _canvas;

    private ArrayList<Entity> _entities = new ArrayList<>();
    Random _rng = new Random();

    volatile boolean _isBoosting = false;
    float _playerSpeed = 0f;

    public Game(Context context) {
        super(context);
        Entity._game = this;
        _holder = getHolder();
        _holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        _paint = new Paint();

        for (int i = 0; i < STAR_COUNT; i++) {
            _entities.add(new Star());
        }
        for (int i = 0; i < ENEMY_COUNT; i++) {
            _entities.add(new Enemy());
        }
        _entities.add(new Player());
    }

    @Override
    public void run() {
        while (_isRunning) {
            input();
            update();
            render();
        }
    }

    private void input() {
    }

    private void update() {
        for (Entity entity : _entities) {
            entity.update();
        }
    }

    private void render() {
        if (!acquireAndLockCanvas()) return;

        _canvas.drawColor(Color.BLACK);

        for (Entity entity : _entities) {
            entity.render(_canvas, _paint);
        }
        
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

    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        for (Entity entity : _entities) {
            entity.destroy();
        }

        _gameThread = null; // not necessary but good practaice
        Entity._game = null;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP: //finger lifted
                _isBoosting = false;
                break;
            case MotionEvent.ACTION_DOWN: //finger pressed
                _isBoosting = true;
                break;
        }
        return true;
    }
}
