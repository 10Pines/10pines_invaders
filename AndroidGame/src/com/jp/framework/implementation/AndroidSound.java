package com.jp.framework.implementation;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.jp.framework.Audio;
import com.jp.framework.Sound;

public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;
	Audio audio;

    public AndroidSound(SoundPool soundPool, int soundId, Audio audio) {
        this.soundId = soundId;
        this.soundPool = soundPool;
        this.audio = audio;
       
    }

    @Override
    public void play() {
    	float volume = audio.getVolume();
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

}
 