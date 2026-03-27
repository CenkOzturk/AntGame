package com.kroq.antsurvivalgame.utils

import android.content.Context
import android.media.MediaPlayer
import com.kroq.antsurvivalgame.R

object MusicManager {

    private var mediaPlayer: MediaPlayer? = null
    var musicEnabled = true

    fun init(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bg_underwater)
            mediaPlayer?.isLooping = true
            mediaPlayer?.setVolume(0.35f, 0.35f)
        }
    }

    fun play() {
        if (musicEnabled && mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}