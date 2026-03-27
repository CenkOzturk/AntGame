package com.kroq.antsurvivalgame.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.kroq.antsurvivalgame.model.Ant

@Composable
fun AntView(
    ants: List<Ant>,
    onAntTap: (Float, Float) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(ants.size) {
                awaitPointerEventScope {
                    while (true) {
                        val down = awaitFirstDown()
                        onAntTap(down.position.x, down.position.y)
                    }
                }
            }

    ) {
        ants.forEach { ant ->
            // HEALTH BAR
            val healthRatio = (ant.hp.toFloat() - 1) / (ant.type.maxHp - 1)

            if (ant.hp > 1) {
                drawArc(
                    color = Color.Green,
                    startAngle = -90f,
                    sweepAngle = 360f * healthRatio,
                    useCenter = false,
                    style = Stroke(width = ant.type.hpStrokeRadius),
                    topLeft = Offset(
                        ant.x - ant.type.radius - 4f,
                        ant.y - ant.type.radius - 4f
                    ),
                    size = Size(
                        (ant.type.radius + 4f) * 2,
                        (ant.type.radius + 4f) * 2
                    )
                )
            }

            drawBubble(
                center = Offset(ant.x, ant.y),
                radius = ant.type.radius + 6f,
                healthRatio
            )
        }
    }
}

fun DrawScope.drawBubble(
    center: Offset,
    radius: Float,
    healthRatio: Float
) {
    // Ana baloncuk
    drawCircle(
        color = Color.Blue, // yarı saydam mor
        radius = radius,
        center = center
    )

    // Parlak kenar (cam efekti)
    drawCircle(
        color = Color.White.copy(alpha = 0.7f),
        radius = radius,
        center = center,
        style = Stroke(width = 3f)
    )

    // Üstte parlama (highlight)
    drawCircle(
        color = Color.White.copy(alpha = 0.5f),
        radius = radius * 0.35f,
        center = Offset(
            center.x - radius * 0.35f,
            center.y - radius * 0.35f
        )
    )
}
