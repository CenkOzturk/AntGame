package com.kroq.antsurvivalgame.model

import androidx.compose.ui.graphics.Color
import com.kroq.antsurvivalgame.R

enum class AntType(
    val speed: Float,
    val hpStrokeRadius: Float,
    val color: Color,
    val radius: Float,
    val maxHp: Int,
    val scoreValue: Int,
    val soundRes: Int
) {
    WORKER(
        speed = 2.5f,
        hpStrokeRadius = 16f,
        color = Color.Black,
        radius = 40f,
        maxHp = 1,
        scoreValue = 1,
        soundRes = R.raw.bubble
    ),
    SOLDIER(
        speed = 7.5f,
        hpStrokeRadius = 16f,
        color = Color.Red,
        radius = 60f, // 🔥 DAHA BÜYÜK
        maxHp = 1,
        scoreValue = 3,
        soundRes = R.raw.big_bubble
    ),
    /*QUEEN(
        speed = 2.4f,      // soldier'dan yavaş
        hpStrokeRadius = 16f,
        color = Color.Cyan,
        radius = 100f,      // 👑 en büyük
        maxHp = 3,
        scoreValue = 10,
        soundRes = R.raw.big_bubble
    )*/
}
