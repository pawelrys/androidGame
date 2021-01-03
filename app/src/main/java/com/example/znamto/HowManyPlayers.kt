package com.example.znamto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

@Suppress("DEPRECATION")
class HowManyPlayers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.how_many_players)

        val howManyButton = findViewById<Button>(R.id.howManyPlayersButton)
        howManyButton.setOnClickListener {
            val edit = findViewById<EditText>(R.id.howManyPlayersEdit)
            if(edit.text.toString().toInt() <= 1) {
                val observer = Observer(PrintTextListener(), this)
                observer.text = "Za mała ilość graczy, by rozpocząć ten tryb rozgrywki! Wpisz większą liczbę graczy!"
                return@setOnClickListener
            }
            else {
                TypePlayerName.howManyPlayers = edit.text.toString().toInt()
                TypePlayerName.composite = Composite()
                val intent = Intent(this, TypePlayerName::class.java)
                startActivity(intent)
            }
        }
    }











    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(true)
        hideSystemUI()
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

