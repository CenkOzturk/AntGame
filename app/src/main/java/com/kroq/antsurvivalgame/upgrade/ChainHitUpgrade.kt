package com.kroq.antsurvivalgame.upgrade

import com.kroq.antsurvivalgame.model.Ant

class ChainHitUpgrade(
    private var level: Int = 1
) : Upgrade {

    override val type = UpgradeType.CHAIN_HIT

    override fun onAntKilled(
        killedAnt: Ant,
        ants: List<Ant>
    ): List<Ant> {

        val result = mutableListOf<Ant>()
        var current = killedAnt

        repeat(level) {
            val next = ants
                .filter { it !in result && it.id != killedAnt.id }
                .minByOrNull { distance(it, current) }

            next?.let {
                result.add(it)
                current = it
            } ?: return result
        }

        return result
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