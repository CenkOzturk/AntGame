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

            UpgradeType.DOUBLE_DAMAGE -> {
                val existing = upgrades[type] as? DoubleDamageUpgrade
                if (existing != null) {
                    existing.levelUp()
                } else {
                    upgrades[type] = DoubleDamageUpgrade().apply { levelUp() }
                }
            }

            UpgradeType.ADD_SOLDIER -> {

            }
        }
    }

    fun getDamage(): Int {
        val damageUpgrade = upgrades[UpgradeType.DOUBLE_DAMAGE] as? DoubleDamageUpgrade
        return damageUpgrade?.getDamage() ?: 1
    }

    fun onAntKilled(
        affectedAnts: Ant,
        ants: List<Ant>
    ): ChainResult {

        val allKills = mutableListOf<Ant>()
        val allPairs = mutableListOf<Pair<Ant, Ant>>()

        upgrades.values.forEach { upgrade ->
            val result = upgrade.onAntKilled(affectedAnts, ants)

            allKills += result.affectedAnts
            allPairs += result.chainPairs
        }

        return ChainResult(allKills, allPairs)
    }

    fun hasUpgrade(type: UpgradeType): Boolean {
        return upgrades.containsKey(type)
    }
}