package com.kroq.antsurvivalgame.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kroq.antsurvivalgame.model.AntType
import com.kroq.antsurvivalgame.ui.AntView
import com.kroq.antsurvivalgame.ui.upgrade.UpgradePopup
import com.kroq.antsurvivalgame.utils.GameConfig
import com.kroq.antsurvivalgame.utils.MusicManager
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

    Box(
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxSize()
            .onSizeChanged {
                screenWidth = it.width.toFloat()
                screenHeight = it.height.toFloat()
                viewModel.init(screenWidth, screenHeight)
            }
    ) {

        AntView(
            ants = viewModel.ants,
            onAntTap = { x, y ->
                viewModel.onAntClicked(x, y, screenWidth, screenHeight)
            }
        )

        Text(
            color = Color.Yellow,
            text = "Score: " + viewModel.score + " / " + viewModel.currentLevel.targetScore,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        )

        Button(
            onClick = { showUpgradePopup = true }) {
            Text("Show Upgrade")
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.setCurrentLevel(level)
            viewModel.startGameLoop(screenWidth, screenHeight)
            delay(16L)
        }
    }

    LaunchedEffect(viewModel.isLevelDone) {
        if (viewModel.isLevelDone)
            onLevelComplete(viewModel.score)
    }

    LaunchedEffect(viewModel.hasQueen, viewModel.ants) {
        if (!viewModel.hasQueen) return@LaunchedEffect

        while (true) {
            delay(GameConfig.WORKER_SPAWN_INTERVAL)

            val workerCount = viewModel.ants.count { it.type == AntType.WORKER }
            if (workerCount >= GameConfig.MAX_WORKERS) continue
        }
    }

    LaunchedEffect(Unit) {
        MusicManager.play()
    }

    if (showUpgradePopup) {
        UpgradePopup(
            onUpgradeSelected = { selected ->
                viewModel.addUpgrade(selected)
                Utils.ToastUtil.show(selected.name)
                showUpgradePopup = false
            }
        )
    }
}