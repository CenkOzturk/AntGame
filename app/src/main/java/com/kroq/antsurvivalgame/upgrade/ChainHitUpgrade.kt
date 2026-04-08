package com.kroq.antsurvivalgame.upgrade

import com.kroq.antsurvivalgame.model.Ant

class ChainHitUpgrade(
    private var level: Int = 1
) : Upgrade {

    override val type = UpgradeType.CHAIN_HIT

    override fun onAntKilled(
        affectedAnts: Ant,
        ants: List<Ant>
    ): ChainResult {

        val killed = mutableListOf<Ant>()
        val pairs = mutableListOf<Pair<Ant, Ant>>()

        var current = affectedAnts

        repeat(level) {

            val next = ants
                .filter { it !in killed && it.id != affectedAnts.id }
                .minByOrNull { distance(it, current) }

            next?.let {
                killed.add(it)
                pairs.add(current to it) // 🔥 bağlantı

                current = it
            } ?: return ChainResult(killed, pairs)
        }

        return ChainResult(killed, pairs)
    }

    fun levelUp() {
        level++
    }

    private fun distance(a: Ant, b: Ant): Float {
        val dx = a.x - b.x
        val dy = a.y - b.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}