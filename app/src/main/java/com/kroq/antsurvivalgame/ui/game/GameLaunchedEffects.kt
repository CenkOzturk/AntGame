package com.kroq.antsurvivalgame.ui.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kroq.antsurvivalgame.utils.MusicManager
import kotlinx.coroutines.delay

@Composable
fun GameLaunchedEffects(
    viewModel: GameViewModel,
    level: Int,
    screenWidth: Float,
    screenHeight: Float,
    onLevelComplete: (Int) -> Unit
) {

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.setCurrentLevel(level)
            viewModel.startGameLoop(screenWidth, screenHeight)
            delay(16L)
        }
    }

    LaunchedEffect(viewModel.isLevelDone) {
        if (viewModel.isLevelDone) {
            onLevelComplete(viewModel.score)
        }
    }

    LaunchedEffect(Unit) {
        MusicManager.play()
    }
}