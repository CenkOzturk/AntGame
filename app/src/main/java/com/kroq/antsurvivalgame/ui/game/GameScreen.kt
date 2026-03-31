package com.kroq.antsurvivalgame.ui.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kroq.antsurvivalgame.data.GameSession
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
    val alpha = remember { Animatable(0f) }
    val flicker = remember { Animatable(1f) }
    val shake = remember { Animatable(0f) }
    val flash = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxSize()
            .onSizeChanged {
                screenWidth = it.width.toFloat()
                screenHeight = it.height.toFloat()
                viewModel.init(screenWidth, screenHeight)
            }
            .offset {
                IntOffset(
                    (Math.random() * shake.value).toInt(),
                    (Math.random() * shake.value).toInt()
                )
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

        Canvas(modifier = Modifier.fillMaxSize()) {

            viewModel.chainEffect.forEach { (from, to) ->

                val start = Offset(from.x, from.y)
                val end = Offset(to.x, to.y)

                val paths = viewModel.generateLightning(start, end)

                paths.forEachIndexed { index, points ->

                    val path = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        points.drop(1).forEach { lineTo(it.x, it.y) }
                    }

                    // 🔥 glow
                    drawPath(
                        path = path,
                        color = Color(0xFF00E5FF).copy(alpha = 0.25f),
                        style = Stroke(width = if (index == 0) 12f else 6f)
                    )

                    // ⚡ core
                    drawPath(
                        path = path,
                        color = Color.White,
                        style = Stroke(width = if (index == 0) 4f else 2f)
                    )
                }

                // 👇 TAM BURAYA EKLE (pathlerden sonra)
                // glow
                drawCircle(
                    color = Color(0xFF00E5FF).copy(alpha = 0.3f),
                    radius = 16f,
                    center = end
                )

                // core
                drawCircle(
                    color = Color.White,
                    radius = 8f,
                    center = end
                )
            }
        }

        if (flash.value > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = flash.value * 0.15f))
            )
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

    LaunchedEffect(viewModel.chainEffect) {
        if (viewModel.chainEffect.isNotEmpty()) {
            alpha.snapTo(1f)
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(200)
            )
        }
    }

    LaunchedEffect(viewModel.chainEffect) {
        if (viewModel.chainEffect.isNotEmpty()) {
            repeat(3) {
                flicker.animateTo(0.6f, tween(40))
                flicker.animateTo(1f, tween(40))
            }
        }
    }

    LaunchedEffect(viewModel.chainEffect) {
        shake.snapTo(10f)
        shake.animateTo(0f, tween(200))
    }

    LaunchedEffect(viewModel.chainEffect) {
        if (viewModel.chainEffect.isNotEmpty()) {
            flash.snapTo(1f)
            delay(50)
            flash.animateTo(0f, tween(150))
        }
    }

    if (showUpgradePopup) {
        UpgradePopup(
            onUpgradeSelected = { selected ->
                GameSession.addUpgrade(selected) // 🔥 EKLEDİK
                Utils.ToastUtil.show(selected.name)
                showUpgradePopup = false
            }
        )
    }
}