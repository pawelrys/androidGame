package com.example.znamto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.db.INTEGER

@Suppress("DEPRECATION")
class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        val settingsSetTimeSongEditText = findViewById<EditText>(R.id.timeSongEdit)
        val settingsSetTimeSongButton = findViewById<Button>(R.id.timeSongButton)

        val settingsSetHowManySongsEdit = findViewById<EditText>(R.id.howManySongEdit)
        val settingsHowManySongButton = findViewById<Button>(R.id.howManySongButton)

        val settingsCategoryButton = findViewById<Button>(R.id.settingsCategoryButton)
        val settingsLanguageButton = findViewById<Button>(R.id.settingsLanguageButton)
        val settingsAuthorButton = findViewById<Button>(R.id.settingsAuthorButton)
        val settingsTitleButton = findViewById<Button>(R.id.settingsTitleButton)
        val settingsSongsButton = findViewById<Button>(R.id.settingsShowSongs)

        settingsSetTimeSongButton.setOnClickListener {
            val observer = Observer(PrintTextListener(), this)
            if(settingsSetTimeSongEditText.text.isEmpty()) {
                observer.text = "Napisz ile sekund!"
            } else {
                val time: Long = settingsSetTimeSongEditText.text.toString().toLong()
                StartPlay.timeToSong = time * 1000
                observer.text = "Ustawiono nowy czas!"
            }
        }

        settingsHowManySongButton.setOnClickListener {
            val observer = Observer(PrintTextListener(), this)
            if(settingsSetHowManySongsEdit.text.isEmpty()) {
                observer.text = "Napisz ile piosenek!"
            } else if(settingsSetHowManySongsEdit.text.toString().toInt() == 0) {
                observer.text = "Napisz niezerową liczbę piosenek!"
            } else {
                val time: Int = settingsSetHowManySongsEdit.text.toString().toInt()

                StartPlay.howManySongsForRound = time
                observer.text = "Ustawiono liczbę piosenek!"
            }
        }

        settingsCategoryButton.setOnClickListener {
            FilterSongsData.filterData = "CategoryTable"
            val intent = Intent(this, FilterSongsData::class.java)
            startActivity(intent)
        }

        settingsLanguageButton.setOnClickListener {
            FilterSongsData.filterData = "LanguageTable"
            val intent = Intent(this, FilterSongsData::class.java)
            startActivity(intent)
        }

        settingsAuthorButton.setOnClickListener {
            FilterSongsData.filterData = "AuthorTable"
            val intent = Intent(this, FilterSongsData::class.java)
            startActivity(intent)
        }

        settingsTitleButton.setOnClickListener {
            FilterSongsData.filterData = "SongsTable"
            val intent = Intent(this, FilterSongsData::class.java)
            startActivity(intent)
        }

        settingsSongsButton.setOnClickListener {
            val intent = Intent(this, AllSongsPage::class.java)
            startActivity(intent)
        }
    }
















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