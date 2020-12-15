package com.example.znamto

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

@Suppress("DEPRECATION")
class AddNewSong : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_song)

        val addSongButton = findViewById<Button>(R.id.addSongButton)
        addSongButton.setOnClickListener {
            addNewSong()
        }
    }

    fun addNewSong() {
        val title = findViewById<EditText>(R.id.addTitle)
        val author = findViewById<EditText>(R.id.addAuthor)
        val category = findViewById<EditText>(R.id.addCategory)
        val language = findViewById<EditText>(R.id.addLanguage)
        val observer = Observer(PrintTextListener(), this)

        if(title.text.isEmpty() || author.text.isEmpty() || category.text.isEmpty() || language.text.isEmpty()){
            observer.text = "Uzupełnij puste pola!"
        } else {
            val newSong = Song(title.text.toString(), Adapter.createAuthor(author.text.toString()), category.text.toString(), language.text.toString())
            val db = DataBase(this).getInstance(this)
            db.addSong(newSong)
            observer.text = "Dodano nową piosenkę!"
        }
    }














    //add hide navigation bar
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

