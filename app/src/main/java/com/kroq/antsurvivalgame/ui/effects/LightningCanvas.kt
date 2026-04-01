package com.kroq.antsurvivalgame.ui.effects

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.kroq.antsurvivalgame.model.Ant

@Composable
fun LightningCanvas(
    chainEffect: List<Pair<Ant, Ant>>,
    generateLightning: (Offset, Offset) -> List<List<Offset>>
) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        chainEffect.forEach { (from, to) ->

            val start = Offset(from.x, from.y)
            val end = Offset(to.x, to.y)

            val paths = generateLightning(start, end)

            paths.forEachIndexed { index, points ->

                val path = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    points.drop(1).forEach { lineTo(it.x, it.y) }
                }

                drawPath(
                    path = path,
                    color = Color(0xFF00E5FF).copy(alpha = 0.25f),
                    style = Stroke(width = if (index == 0) 12f else 6f)
                )

                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = if (index == 0) 4f else 2f)
                )
            }

            // impact
            drawCircle(
                color = Color(0xFF00E5FF).copy(alpha = 0.3f),
                radius = 16f,
                center = end
            )

            drawCircle(
                color = Color.White,
                radius = 8f,
                center = end
            )
        }
    }
}