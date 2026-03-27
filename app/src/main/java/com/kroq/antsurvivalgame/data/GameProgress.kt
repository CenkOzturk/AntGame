package com.kroq.antsurvivalgame.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class GameProgress(
    val level: Int = 1,
    val score: Int = 0
)

object GameSession {
    var progress by mutableStateOf(GameProgress())
        private set

    fun reset() {
        progress = GameProgress()
    }

    fun setScore(value: Int) {
        progress = progress.copy(score = value)
    }

    fun nextLevel() {
        progress = progress.copy(level = progress.level + 1)
    }
}