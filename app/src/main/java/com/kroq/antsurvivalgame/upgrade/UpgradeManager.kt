package com.kroq.antsurvivalgame.upgrade

import com.kroq.antsurvivalgame.model.Ant

class UpgradeManager {

    private val upgrades = mutableMapOf<UpgradeType, Upgrade>()

    fun addUpgrade(type: UpgradeType) {
        when (type) {
            UpgradeType.CHAIN_HIT -> {
                val existing = upgrades[type] as? ChainHitUpgrade
                if (existing != null) {
                    existing.levelUp()
                } else {
                    upgrades[type] = ChainHitUpgrade()
                }
            }

            UpgradeType.DOUBLE_SCORE -> {

            }

            UpgradeType.ADD_SOLDIER -> {

            }
        }
    }

    fun onAntKilled(
        killedAnt: Ant,
        ants: List<Ant>
    ): List<Ant> {

        val extraKills = mutableListOf<Ant>()

        upgrades.values.forEach { upgrade ->
            extraKills += upgrade.onAntKilled(killedAnt, ants)
        }

        return extraKills
    }

    fun hasUpgrade(type: UpgradeType): Boolean {
        return upgrades.containsKey(type)
    }
}