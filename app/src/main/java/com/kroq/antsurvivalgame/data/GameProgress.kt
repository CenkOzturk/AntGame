package com.kroq.antsurvivalgame.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kroq.antsurvivalgame.upgrade.UpgradeManager
import com.kroq.antsurvivalgame.upgrade.UpgradeType

data class GameProgress(
    val level: Int = 1,
    val score: Int = 0
)

object GameSession {

    var progress by mutableStateOf(GameProgress())
        private set

    // 🔥 EKLEDİK
    val upgradeManager = UpgradeManager()

    fun setScore(value: Int) {
        progress = progress.copy(score = value)
    }

    fun nextLevel() {
        progress = progress.copy(level = progress.level + 1)
    }

    // 🔥 helper (opsiyonel ama temiz)
    fun addUpgrade(type: UpgradeType) {
        upgradeManager.addUpgrade(type)
    }

    fun hasUpgrade(type: UpgradeType): Boolean {
        return upgradeManager.hasUpgrade(type)
    }
}