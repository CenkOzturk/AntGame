package com.kroq.antsurvivalgame.upgrade

import com.kroq.antsurvivalgame.model.Ant

class DoubleDamageUpgrade() : Upgrade {
    override val type = UpgradeType.DOUBLE_DAMAGE
    private var level = 0

    fun levelUp() {
        level++
    }

    fun getDamage(): Int {
        val baseDamage = 1
        return baseDamage + level
    }

    override fun onAntKilled(
        killedAnt: Ant,
        ants: List<Ant>
    ): ChainResult {
        return ChainResult(emptyList(), emptyList())
    }
}
