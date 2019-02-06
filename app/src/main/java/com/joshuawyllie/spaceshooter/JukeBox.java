package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.io.IOException;

public class JukeBox {

    private Context _context;
    private SoundPool _soundPool = null;

    private final int MAX_STREAMS = 3;
    static int CRASH = 0;


    JukeBox(Context context) {
        _context = context;

        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        _soundPool = new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(MAX_STREAMS)
                .build();
        loadSounds(context);
    }

    private void loadSounds(final Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd(context.getString(R.string.colision_sound_file));
            CRASH = _soundPool.load(descriptor, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void play(final int soundID) {
        final float leftVolume = 1f;
        final float rightVolume = 1f;
        final int priority = 0;
        final int loop = 0;
        final float rate = 1.0f;

        if (soundID > 0) {
            _soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    void destroy() {
        _soundPool.release();
        _soundPool = null;
    }
}
