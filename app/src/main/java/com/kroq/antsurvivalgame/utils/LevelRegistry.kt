package com.kroq.antsurvivalgame.utils

import com.kroq.antsurvivalgame.data.LEVELS
import com.kroq.antsurvivalgame.data.LevelConfig

object LevelRegistry {
    private val levelMap: Map<Int, LevelConfig> =
        LEVELS.associateBy { it.level }

    private val maxLevel = LEVELS.maxOf { it.level }

    fun get(level: Int): LevelConfig {
        return levelMap[level]
            ?: levelMap[maxLevel]!!
    }
}
