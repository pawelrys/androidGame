package com.example.znamto

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.start_play.*


@Suppress("DEPRECATION")
class StartPlay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        onWindowFocusChanged(true)
        val songsToShow = randomSongs()
        val iterator = songsToShow.createIterator()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.start_play)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        val startPlayText = findViewById<TextView>(R.id.startPlayText)

        Handler().postDelayed({
            startPlayText.text = "3"
            startPlayText.startAnimation(animation)
            Handler().postDelayed({
                startPlayText.text = "2"
                startPlayText.startAnimation(animation)
                Handler().postDelayed({
                    startPlayText.text = "1"
                    startPlayText.startAnimation(animation)
                    Handler().postDelayed({
                        startPlayText.text = "Go!"
                        startPlayText.startAnimation(animation)
                        showSong(iterator)
                        Handler().postDelayed({
                        }, 500)
                    }, 1000)
                }, 1000)
            }, 1000)
        }, 1000)
    }

    fun randomSongs() : SongsListCollection {
        val songs = SongsListCollection()
        val db = DataBase(this).getInstance(this)
        repeat(3) {
            songs.add(db.getRandomSong())
        }
        return songs
    }

    fun showSong(iterator : SongsListCollection.ListIterator) {
        if(!iterator.isNull()) {
            val song = iterator.next()
            val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
            val startPlayText = findViewById<TextView>(R.id.startPlayText)
            val startPlayAuthorText = findViewById<TextView>(R.id.startPlayAuthorText)
            startPlayText.text = song.titleSong
            startPlayText.startAnimation(animation)
            startPlayAuthorText.text = song.authorSong
            startPlayAuthorText.startAnimation(animation)
            val screen = findViewById<ConstraintLayout>(R.id.startPlayScreen)

            val timer: CountDownTimer = object : CountDownTimer(8000, 1000) {
                override fun onFinish() {
//                    Toast.makeText(applicationContext, "Siema", Toast.LENGTH_SHORT).show()
                    cancel()
                    Handler().postDelayed({
                        showSong(iterator)
                    }, 100)
                }

                override fun onTick(millisUntilFinished: Long) {
                    startPlayTimer.text = (millisUntilFinished / 1000).toString()
                }
            }.start()

            screen.setOnClickListener {
                timer.onFinish()
            }
        }
        else {
            val intent = Intent(this, menuClass::class.java)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            startActivity(intent)
            //Toast.makeText(applicationContext, "Siema", Toast.LENGTH_SHORT).show()

        }
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
            window.decorView.systemUiVisibility = (SYSTEM_UI_FLAG_IMMERSIVE_STICKY
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