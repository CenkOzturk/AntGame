package com.kroq.antsurvivalgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.kroq.antsurvivalgame.navigation.AppNavigation
import com.kroq.antsurvivalgame.utils.GlobalPopupHost
import com.kroq.antsurvivalgame.utils.MusicManager
import com.kroq.antsurvivalgame.utils.SoundManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MusicManager.init(this)
        SoundManager.init(this)
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                AppNavigation()         // Senin ekranın
                GlobalPopupHost()       // Popup host burada olmalı
            }
        }
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }
}







