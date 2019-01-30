package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class Game extends SurfaceView implements Runnable {
    public static final String TAG = "Game";
    private static final int STAGE_WIDTH = 1280;
    private static final int STAGE_HEIGHT = 720 ;
    private Thread _gameThread;
    private volatile boolean _isRunning = false;
    private SurfaceHolder _holder;
    private Paint _paint;
    private Canvas _canvas;

    public Game(Context context) {
        super(context);
        _holder = getHolder();
        _holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        _paint = new Paint();
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
    }

    private void render() {
        if (!_holder.getSurface().isValid()) {
            return;
        }

        _canvas = _holder.lockCanvas();
        if (_canvas == null) {
            return;
        }

        _canvas.drawColor(Color.CYAN);
        
        _holder.unlockCanvasAndPost(_canvas);
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
        _gameThread = null; // not necessary but good practaice
    }
}
