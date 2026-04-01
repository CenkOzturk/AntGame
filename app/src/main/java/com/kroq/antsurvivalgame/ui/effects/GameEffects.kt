package com.kroq.antsurvivalgame.ui.effects

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay

@Composable
fun rememberGameEffects(chainEffect: List<Pair<Any, Any>>): GameEffects {

    val alpha = remember { Animatable(0f) }
    val flicker = remember { Animatable(1f) }
    val shake = remember { Animatable(0f) }
    val flash = remember { Animatable(0f) }

    LaunchedEffect(chainEffect) {
        if (chainEffect.isNotEmpty()) {
            alpha.snapTo(1f)
            alpha.animateTo(0f, tween(200))
        }
    }

    LaunchedEffect(chainEffect) {
        if (chainEffect.isNotEmpty()) {
            repeat(3) {
                flicker.animateTo(0.6f, tween(40))
                flicker.animateTo(1f, tween(40))
            }
        }
    }

    LaunchedEffect(chainEffect) {
        shake.snapTo(10f)
        shake.animateTo(0f, tween(200))
    }

    LaunchedEffect(chainEffect) {
        if (chainEffect.isNotEmpty()) {
            flash.snapTo(1f)
            delay(50)
            flash.animateTo(0f, tween(150))
        }
    }

    return GameEffects(
        alpha = alpha,
        flicker = flicker,
        flash = flash,
        shake = shake
    )
}

data class GameEffects(
    val alpha: Animatable<Float, *>,
    val flicker: Animatable<Float, *>,
    val flash: Animatable<Float, *>,
    val shake: Animatable<Float, *>
) {
    fun shakeOffset() = IntOffset(
        (Math.random() * shake.value).toInt(),
        (Math.random() * shake.value).toInt()
    )

    val flashAlpha get() = flash.value
}