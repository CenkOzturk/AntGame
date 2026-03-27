package com.kroq.antsurvivalgame.model

data class Ant(
    val id: Int,
    val type: AntType,
    val x: Float,
    val y: Float,
    val vx: Float,
    val vy: Float,
    val hp: Int,
    val sizeBonus: Float = 0f, // 👈 YEDİKÇE BÜYÜR
    val nextDirectionChange: Long = System.currentTimeMillis()
)