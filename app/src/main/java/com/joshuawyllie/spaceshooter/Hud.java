package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Hud {

    private static final int HUD_SIZE = 48;
    public static final String PREFS = "com.joshuawyllie.spaceshooter";
    public static final String LONGEST_DIST = "longest_distance";

    private static Context _context = null;
    private SharedPreferences _prefs = null;
    private SharedPreferences.Editor _editor = null;
    private static int _distanceTraveled = 0;
    private static int _maxDistanceTraveled = 0;

    public Hud(Context context) {
        _context = context;
        _prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        _editor = _prefs.edit();
    }

    public void renderHUD(final Canvas canvas, final Paint paint, int health, boolean gameOver) {
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(HUD_SIZE);
        final float centerX = Game.STAGE_WIDTH / 2;
        final float centerY = Game.STAGE_HEIGHT / 2;
        if (gameOver) {
            canvas.drawText(_context.getString(R.string.game_over), centerX, centerY, paint);
            canvas.drawText(_context.getString(R.string.game_start_instructions), centerX, centerY + HUD_SIZE, paint);
        } else {
            canvas.drawText(String.format("%s %d", _context.getString(R.string.health), health), HUD_SIZE, HUD_SIZE, paint);
            canvas.drawText(String.format("%s %d", _context.getString(R.string.distance_traveled), _distanceTraveled), HUD_SIZE, HUD_SIZE * 2, paint);
        }

    }

    public void restart() {
        _distanceTraveled = 0;
        _maxDistanceTraveled = _prefs.getInt(LONGEST_DIST, 0);
    }

    public void update(float playerSpeed) {
        _distanceTraveled += playerSpeed;
    }

    public void gameOver() {
        if (_distanceTraveled > _maxDistanceTraveled) {
            _maxDistanceTraveled = _distanceTraveled;
            _editor.putInt(LONGEST_DIST, _maxDistanceTraveled);
            _editor.apply();
        }
    }
}
