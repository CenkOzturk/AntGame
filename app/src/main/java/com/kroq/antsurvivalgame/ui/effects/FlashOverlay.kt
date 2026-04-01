package com.kroq.antsurvivalgame.ui.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FlashOverlay(alpha: Float) {
    if (alpha > 0f) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = alpha * 0.15f))
        )
    }
}