package com.kroq.antsurvivalgame.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroq.antsurvivalgame.data.GameSession
import com.kroq.antsurvivalgame.data.LEVELS
import com.kroq.antsurvivalgame.model.Ant
import com.kroq.antsurvivalgame.model.AntType
import com.kroq.antsurvivalgame.model.ClickResult
import com.kroq.antsurvivalgame.utils.GameConfig
import com.kroq.antsurvivalgame.utils.LevelRegistry
import com.kroq.antsurvivalgame.utils.SoundManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class GameViewModel : ViewModel() {
    var ants by mutableStateOf<List<Ant>>(emptyList())
        private set

    var score by mutableIntStateOf(0)
        private set

    var currentLevel by mutableStateOf(LEVELS.first())
        private set

    var isLevelDone by mutableStateOf(false)
        private set

    var chainEffect by mutableStateOf<List<Pair<Ant, Ant>>>(emptyList())
        private set

    private var nextAntId = 0

    fun init(screenWidth: Float, screenHeight: Float) {
        ants = List(GameConfig.INITIAL_ANT_COUNT) {
            createAnt(
                id = nextAntId++,
                type = AntType.WORKER,
                screenWidth = screenWidth,
                screenHeight = screenHeight
            )
        }
    }

    fun startGameLoop(screenWidth: Float, screenHeight: Float) {
        ants = ants
            .map { maybeChangeDirection(it) }
            .let { resolveAntCollisions(it) }
            .let { resolveSoldierEating(it) }
            .map { moveAnt(it, screenWidth, screenHeight) }
    }

    fun onAntClicked(
        x: Float,
        y: Float,
        screenWidth: Float,
        screenHeight: Float
    ) {
        val clickedAnt = ants.firstOrNull { ant ->
            hypot(x - ant.x, y - ant.y) <= ant.type.radius
        } ?: return

        // 🔥 1. Upgrade sonucu al (BURASI)
        val chainResult = GameSession.upgradeManager
            .onAntKilled(clickedAnt, ants)

        // 🔥 2. Animasyon için UI state set et
        viewModelScope.launch {
            chainEffect = emptyList()

            chainResult.chainPairs.forEachIndexed { index, pair ->
                delay(index * 30L)
                chainEffect = chainEffect + pair
            }

            delay(150)
            chainEffect = emptyList()
        }

        // 🔥 3. Tüm öldürülecek antlar
        val killQueue = (listOf(clickedAnt) + chainResult.killedAnts)
            .distinctBy { it.id }

        var currentAnts = ants
        var totalScore = 0

        killQueue.forEach { antToKill ->

            val stillExists = currentAnts.any { it.id == antToKill.id }
            if (!stillExists) return@forEach

            val result = handleAntClick(
                ants = currentAnts,
                clicked = antToKill,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                nextId = { nextAntId++ }
            )

            currentAnts = result.ants
            totalScore += result.gainedScore
        }

        ants = currentAnts
        score += totalScore

        controlScoreForLevel()

        // 🔥 4. animasyonu temizle (çok önemli)
        viewModelScope.launch {
            delay(150)
            chainEffect = emptyList()
        }
    }

    fun moveAnt(
        ant: Ant,
        screenWidth: Float,
        screenHeight: Float
    ): Ant {
        val newX = ant.x + ant.vx
        val newY = ant.y + ant.vy

        var newVx = ant.vx
        var newVy = ant.vy

        val size = 24f

        if (newX <= 0 || newX >= screenWidth - size) {
            newVx *= -1
        }

        if (newY <= 0 || newY >= screenHeight - size) {
            newVy *= -1
        }

        return ant.copy(
            x = newX.coerceIn(0f, screenWidth - size),
            y = newY.coerceIn(0f, screenHeight - size),
            vx = newVx,
            vy = newVy
        )
    }

    fun resolveAntCollisions(ants: List<Ant>): List<Ant> {
        val radius = 12f
        val mutableAnts = ants.toMutableList()

        for (i in mutableAnts.indices) {
            for (j in i + 1 until mutableAnts.size) {

                val a = mutableAnts[i]
                val b = mutableAnts[j]

                val dx = b.x - a.x
                val dy = b.y - a.y
                val distance = sqrt(dx * dx + dy * dy)

                if (distance < radius * 2) {
                    val pushX = dx / distance * 1.5f
                    val pushY = dy / distance * 1.5f

                    mutableAnts[i] = a.copy(
                        vx = -a.vx,
                        vy = -a.vy,
                        x = a.x - pushX,
                        y = a.y - pushY
                    )

                    mutableAnts[j] = b.copy(
                        vx = -b.vx,
                        vy = -b.vy,
                        x = b.x + pushX,
                        y = b.y + pushY
                    )
                }
            }
        }

        return mutableAnts
    }

    fun maybeChangeDirection(ant: Ant): Ant {
        val now = System.currentTimeMillis()

        if (now > ant.nextDirectionChange) {
            val angle = Random.nextFloat() * 2f * Math.PI.toFloat()

            return ant.copy(
                vx = cos(angle) * ant.type.speed,
                vy = sin(angle) * ant.type.speed,
                nextDirectionChange = now + (1000L..3000L).random()
            )
        }

        return ant
    }

    fun createAnt(
        id: Int,
        type: AntType,
        screenWidth: Float,
        screenHeight: Float
    ): Ant {
        val angle = Random.nextFloat() * 2f * Math.PI.toFloat()

        return Ant(
            id = id,
            type = type,
            x = Random.nextFloat() * screenWidth,
            y = Random.nextFloat() * screenHeight,
            vx = cos(angle) * type.speed,
            vy = sin(angle) * type.speed,
            hp = type.maxHp
        )
    }

    fun handleAntClick(
        ants: List<Ant>,
        clicked: Ant,
        screenWidth: Float,
        screenHeight: Float,
        nextId: () -> Int
    ): ClickResult {
        val updatedHealth = clicked.hp - 1

        // 🟡 ANT HÂLÂ YAŞIYORSA
        if (updatedHealth > 0) {
            val newAnts = ants.map {
                if (it.id == clicked.id) it.copy(hp = updatedHealth)
                else it
            }

            return ClickResult(
                ants = newAnts,
                gainedScore = 0,
                //hasQueen = hasQueen
            )
        }

        SoundManager.playPop(clicked.type)

        // 🔴 ANT ÖLDÜ
        val gainedScore: Int = clicked.type.scoreValue

        val remainingAnts = ants.filterNot { it.id == clicked.id }

        val spawnedAnts =
            createAnt(
                id = nextId(),
                type = AntType.WORKER,
                screenWidth,
                screenHeight
            )

        return ClickResult(
            ants = remainingAnts + spawnedAnts,
            gainedScore = gainedScore,
        )
    }

    fun resolveSoldierEating(ants: List<Ant>): List<Ant> {
        val result = ants.toMutableList()

        val soldiers = result.filter { it.type == AntType.SOLDIER }
        val workers = result.filter { it.type == AntType.WORKER }

        val toRemove = mutableSetOf<Int>()
        val updatedSoldiers = mutableMapOf<Int, Ant>()

        for (soldier in soldiers) {
            for (worker in workers) {
                val dx = worker.x - soldier.x
                val dy = worker.y - soldier.y
                val dist = hypot(dx, dy)

                if (dist < soldier.type.radius + worker.type.radius) {
                    toRemove.add(worker.id)

                    updatedSoldiers[soldier.id] =
                        soldier.copy(sizeBonus = soldier.sizeBonus + 2f)
                }
            }
        }

        return result
            .filterNot { toRemove.contains(it.id) }
            .map { updatedSoldiers[it.id] ?: it }
    }

    fun setCurrentLevel(level: Int) {
        currentLevel = LevelRegistry.get(level)
    }

    fun controlScoreForLevel() {
        if (score >= currentLevel.targetScore)
            isLevelDone = true
    }

    fun generateLightning(
        start: Offset,
        end: Offset,
        displacement: Float = 40f,
    ): List<List<Offset>> {

        fun subdivide(a: Offset, b: Offset, dis: Float): List<Offset> {
            if (dis < 5f) return listOf(a, b)

            val mid = Offset(
                (a.x + b.x) / 2,
                (a.y + b.y) / 2
            )

            val offset = Offset(
                ((Math.random() - 0.5) * dis).toFloat(),
                ((Math.random() - 0.5) * dis).toFloat()
            )

            val midDisplaced = mid + offset

            return subdivide(a, midDisplaced, dis / 2) +
                    subdivide(midDisplaced, b, dis / 2).drop(1)
        }

        val main = subdivide(start, end, displacement)

        // 🔥 branch oluştur
        val branches = mutableListOf<List<Offset>>()

        main.forEachIndexed { i, point ->
            if (i > 2 && Math.random() < 0.3) {
                val branchEnd = Offset(
                    point.x + ((Math.random() - 0.5) * 100).toFloat(),
                    point.y + ((Math.random() - 0.5) * 100).toFloat()
                )

                branches.add(subdivide(point, branchEnd, displacement / 2))
            }
        }

        return listOf(main) + branches
    }
}
