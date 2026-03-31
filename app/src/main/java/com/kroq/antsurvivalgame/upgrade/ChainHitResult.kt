package com.kroq.antsurvivalgame.upgrade

import com.kroq.antsurvivalgame.model.Ant

data class ChainResult(
    val killedAnts: List<Ant>,
    val chainPairs: List<Pair<Ant, Ant>> // 🔥 animasyon için
)