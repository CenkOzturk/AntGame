package com.kroq.antsurvivalgame.utils

import android.content.Context
import android.media.SoundPool
import com.kroq.antsurvivalgame.R
import com.kroq.antsurvivalgame.model.AntType

object SoundManager {

    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<Int, Int>()

    fun init(context: Context) {
        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        AntType.entries.forEach { type ->
            val soundId = soundPool!!.load(context, type.soundRes, 1)
            soundMap[type.soundRes] = soundId
        }
    }

    fun playPop(type: AntType) {
        val soundId = soundMap[type.soundRes] ?: return
        val randomPitch = (90..110).random() / 100f
        soundPool?.play(
            soundId,
            1f,  // sol volume
            1f,  // sağ volume
            1,   // priority
            0,   // loop
            randomPitch // pitch
        )
    }

    fun playChain() {
        val soundId = soundMap[R.raw.buzz] ?: return
        val randomPitch = (90..110).random() / 100f
        soundPool?.play(
            soundId,
            1f,  // sol volume
            1f,  // sağ volume
            1,   // priority
            0,   // loop
            randomPitch // pitch
        )
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}