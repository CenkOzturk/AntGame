package com.kroq.antsurvivalgame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kroq.antsurvivalgame.data.GameSession
import com.kroq.antsurvivalgame.ui.game.GameScreen
import com.kroq.antsurvivalgame.ui.levelComplete.LevelCompleteScreen
import com.kroq.antsurvivalgame.ui.menu.MainMenuScreen
import com.kroq.antsurvivalgame.ui.splash.SplashScreen
import com.kroq.antsurvivalgame.upgrade.Upgrade

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.Menu.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Menu.route) {
            MainMenuScreen {
                navController.navigate(Screen.Game.route)
            }
        }

        composable(Screen.Game.route) {
            GameScreen(
                level = GameSession.progress.level,
                onLevelComplete = { score ->
                    GameSession.setScore(score)
                    navController.navigate(Screen.LevelComplete.route)
                }
            )
        }

        composable(Screen.LevelComplete.route) {
            LevelCompleteScreen(
                level = GameSession.progress.level,
                score = GameSession.progress.score,
                onContinue = {
                    GameSession.nextLevel()
                    navController.navigate(Screen.Game.route) {
                        launchSingleTop = true
                    }
                },
                onUpgradeSelected = {
                    GameSession.addUpgrade(it)
                    GameSession.nextLevel()
                }
            )
        }
    }
}
