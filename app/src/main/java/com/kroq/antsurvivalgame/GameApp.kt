package com.kroq.antsurvivalgame

import android.app.Application
import com.kroq.antsurvivalgame.utils.Utils

class GameApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}
