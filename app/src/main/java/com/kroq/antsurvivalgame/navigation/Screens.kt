package com.kroq.antsurvivalgame.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Menu : Screen("menu")
    object Game : Screen("game")

    object LevelComplete : Screen("level_complete")
}