package com.kroq.antsurvivalgame.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kroq.antsurvivalgame.data.GameSession
import com.kroq.antsurvivalgame.ui.AntView
import com.kroq.antsurvivalgame.ui.effects.FlashOverlay
import com.kroq.antsurvivalgame.ui.effects.LightningCanvas
import com.kroq.antsurvivalgame.ui.effects.rememberGameEffects
import com.kroq.antsurvivalgame.ui.upgrade.UpgradePopup
import com.kroq.antsurvivalgame.utils.Utils
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    level: Int,
    onLevelComplete: (score: Int) -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    var screenWidth by remember { mutableStateOf(0f) }
    var screenHeight by remember { mutableStateOf(0f) }
    var showUpgradePopup by remember { mutableStateOf(false) }
    val effects = rememberGameEffects(viewModel.chainEffect)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .onSizeChanged {
                screenWidth = it.width.toFloat()
                screenHeight = it.height.toFloat()
                viewModel.init(screenWidth, screenHeight)
            }
            .offset { effects.shakeOffset() }
    ) {
        AntView(
            ants = viewModel.ants,
            onAntTap = { x, y ->
                viewModel.onAntClicked(x, y, screenWidth, screenHeight)
            }
        )

        GameOverlay(
            score = viewModel.score,
            target = viewModel.currentLevel.targetScore,
            onUpgradeClick = { showUpgradePopup = true }
        )

        LightningCanvas(
            chainEffect = viewModel.chainEffect,
            generateLightning = viewModel::generateLightning
        )

        FlashOverlay(effects.flashAlpha)
    }

    if (screenWidth > 0f && screenHeight > 0f) {
        GameLaunchedEffects(
            viewModel = viewModel,
            level = level,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            onLevelComplete = onLevelComplete
        )
    }

    if (showUpgradePopup) {
        UpgradePopup(
            onUpgradeSelected = {
                GameSession.addUpgrade(it)
                Utils.ToastUtil.show(it.name)
                showUpgradePopup = false
            }
        )
    }
}