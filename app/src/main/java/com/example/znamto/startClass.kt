package com.example.znamto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.start_page.*
import kotlinx.coroutines.*
import org.jetbrains.anko.ScreenSize


@Suppress("DEPRECATION")
class startClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var isClick : Boolean = false
        onWindowFocusChanged(true)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.start_page)
        val startTitle = findViewById<TextView>(R.id.startText)
        val startSecondTitle = findViewById<TextView>(R.id.startSecondText)
        startSecondTitle.text = ""
        val b = AnimationUtils.loadAnimation(this, R.anim.fadein)
        startTitle.startAnimation(b)

        Handler().postDelayed({
            startTitle.text = ""
            startSecondTitle.text = "Zaczynajmy zabawę!"
            startSecondTitle.startAnimation(b)
            Handler().postDelayed({
                startSecondTitle.text = ""
                val intent = Intent(this, menuClass::class.java)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                startActivity(intent)
            }, 2000)
        }, 2000)


    }

    //add hide navigation bar
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}