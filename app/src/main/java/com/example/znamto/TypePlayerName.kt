package com.example.znamto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

@Suppress("DEPRECATION")
class TypePlayerName : AppCompatActivity() {
    companion object {
        var howManyPlayers : Int = 0
        var composite : Composite = Composite()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.type_player_name)

        val typeButton = findViewById<Button>(R.id.typePlayersNameButton)
        val typeText = findViewById<TextView>(R.id.typePlayersNameText)
        val typeEdit = findViewById<EditText>(R.id.typePlayersNameEdit)


        var idx = 0
        typeText.text = "Wpisz nazwę ${idx + 1} gracza!"

        val observer = Observer(PrintTextListener(), this)
        typeButton.setOnClickListener {
            if(typeEdit.text.isEmpty()) {
                observer.text = "Podaj nazwę gracza!"
                return@setOnClickListener
            } else {
                val a = typeEdit.text.toString()
                if(composite.isExist(Player(typeEdit.text.toString())) == 1) {
                    observer.text = "Taka nazwa gracza już istnieje! Wpisz inną nazwę!"
                    return@setOnClickListener
                }
                composite.add(Player(typeEdit.text.toString()))
                idx++
                if(idx == howManyPlayers) {
                    val intent = Intent(this, StartPlay::class.java)
                    startActivity(intent)
                } else {
                    StartPlay.playWithFriends = true
                    StartPlay.howManyPlayers = howManyPlayers
                    StartPlay.nowPlay = 0
                    StartPlay.composite = composite
                    typeText.text = "Wpisz nazwę ${idx + 1} gracza!"
                    typeEdit.text.clear()
                }
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