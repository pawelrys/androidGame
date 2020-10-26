package com.example.znamto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class menuClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.menu_page)
    }

    override fun finish() {
        minimizeApp()
    }

    fun minimizeApp() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }
}