package com.kroq.antsurvivalgame.upgrade

import com.kroq.antsurvivalgame.model.Ant

interface Upgrade {
    val type: UpgradeType

    fun onAntKilled(
        affectedAnts: Ant,
        ants: List<Ant>
    ): ChainResult
}