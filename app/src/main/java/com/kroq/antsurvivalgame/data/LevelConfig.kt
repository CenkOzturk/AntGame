package com.kroq.antsurvivalgame.data

data class LevelConfig(
    val level: Int,
    val targetScore: Int,
    val maxWorkers: Int,
    val soldierEnabled: Boolean,
    val queenEnabled: Boolean
)

val LEVELS = listOf(

    // ---- Early Game ----
    LevelConfig(1, 5, 5, false, false),
    LevelConfig(2, 8, 6, true, false),
    LevelConfig(3, 12, 8, true, false),
    LevelConfig(4, 18, 10, true, true),
    LevelConfig(5, 25, 12, true, true),
    LevelConfig(6, 35, 14, true, true),
    LevelConfig(7, 50, 16, true, true),
    LevelConfig(8, 65, 18, true, true),
    LevelConfig(9, 80, 20, true, true),

    // ---- BOSS ----
    LevelConfig(10, 150, 0, false, false), // Boss level

    // ---- Mid Game ----
    LevelConfig(11, 100, 22, true, true),
    LevelConfig(12, 120, 25, true, true),
    LevelConfig(13, 150, 28, true, true),
    LevelConfig(14, 180, 30, true, true),
    LevelConfig(15, 220, 32, true, true),
    LevelConfig(16, 260, 35, true, true),
    LevelConfig(17, 300, 38, true, true),
    LevelConfig(18, 350, 42, true, true),
    LevelConfig(19, 420, 45, true, true),

    // ---- BOSS ----
    LevelConfig(20, 300, 0, false, false) // Boss level
)

