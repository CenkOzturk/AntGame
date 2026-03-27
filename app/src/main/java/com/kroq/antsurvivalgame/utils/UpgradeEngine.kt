package com.kroq.antsurvivalgame.utils

import com.kroq.antsurvivalgame.model.Ant

object UpgradeEngine {
    fun chainHit(
        startAnt: Ant,
        ants: List<Ant>,
        chainLevel: Int
    ): List<Ant> {

        val killed = mutableListOf<Ant>()
        var current = startAnt

        repeat(chainLevel + 1) {

            killed.add(current)

            val next = ants
                .filter { it !in killed }
                .minByOrNull { distance(it, current) }

            current = next ?: return killed
        }

        return killed
    }

    private fun distance(a: Ant, b: Ant): Float {
        val dx = a.x - b.x
        val dy = a.y - b.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}