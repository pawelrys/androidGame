package com.example.znamto

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class fastStart : AppCompatActivity() {
//    val db = DataBase.getInstance(fastStart.applicationContext());

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.fast_start)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    init {
        instance = this
    }

    companion object {

        private var instance: fastStart? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    fun applicationContext() : Context {
        return instance!!.applicationContext
    }

}