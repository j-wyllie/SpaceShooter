package com.joshuawyllie.spaceshooter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

public class JukeBox {

    public static final String TAG = "JukeBox";
    private static final float DEFAULT_SFX_VOLUME = 1f;
    private Context _context;
    private SoundPool _soundPool = null;

    private final int MAX_STREAMS = 3;
    static int CRASH = 0;
    static int BOOST = 0;
    static int DEATH = 0;
    static int START = 0;
    private HashMap<GameEvent, Integer> _sfxMap;


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
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        _sfxMap = new HashMap();
        loadEventSound(GameEvent.COLLISION, _context.getString(R.string.COLLISON_SOUND)); //TODO: move to config file
        loadEventSound(GameEvent.BOOST, "boost.wav");
        loadEventSound(GameEvent.DEATH, "death.wav");
        loadEventSound(GameEvent.LEVEL_START, "start.wav");
    }

    public void play(final GameEvent event) {
        final float leftVolume = DEFAULT_SFX_VOLUME;
        final float rightVolume = DEFAULT_SFX_VOLUME;
        final int priority = 1;
        final int loop = 0; //-1 loop forever, 0 play once
        final float rate = 1.0f;
        final Integer soundID = _sfxMap.get(event);
        if (soundID != null) {
            _soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    void destroy() {
        if (_soundPool != null) {
            _soundPool.release();
            _soundPool = null;
        }
    }

    public void playSoundForGameEvent(GameEvent event) {
    }

    private void loadEventSound(final GameEvent event, final String fileName) {
        try {
            final AssetFileDescriptor afd = _context.getAssets().openFd(fileName);
            final int soundId = _soundPool.load(afd, 1);
            _sfxMap.put(event, soundId);
        } catch (IOException e) {
            Log.e(TAG, "loadEventSound: error loading sound " + e.toString());
        }
    }

}
