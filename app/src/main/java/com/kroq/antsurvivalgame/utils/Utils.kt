package com.kroq.antsurvivalgame.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

object Utils {
    var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }
    object ToastUtil {

        fun show(message: String) {
            appContext?.let {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            } ?: Log.e("ToastUtil", "Context henüz init edilmemiş!")
        }
    }

    object PopupUtil {
        private val _message = mutableStateOf<String?>(null)
        val message: State<String?> = _message

        fun show(message: String) {
            _message.value = message
        }

        fun dismiss() {
            _message.value = null
        }
    }
}