package com.example.znamto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MenuClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)

        val fastStartButton = findViewById<Button>(R.id.fastStartButton)
        fastStartButton.setOnClickListener {
            val db = DataBase(this).getInstance(this)
            if(db.getManyRandomSong(StartPlay.howManySongsForRound) == ArrayList<Song>()) {
                val observer = Observer(PrintTextListener(), this)
                observer.text = "Nie ma tylu zaznaczonych piosenek w bazie danych!"
                return@setOnClickListener
            }
            StartPlay.playWithFriends = false
            val intent = Intent(this, StartPlay::class.java)
            startActivity(intent)
        }

        val playFriends = findViewById<Button>(R.id.startWithFriendsButton)
        playFriends.setOnClickListener {
            val db = DataBase(this).getInstance(this)
            if(db.getManyRandomSong(StartPlay.howManySongsForRound) == ArrayList<Song>()) {
                val observer = Observer(PrintTextListener(), this)
                observer.text = "Nie ma tylu zaznaczonych piosenek w bazie danych!"
                return@setOnClickListener
            }
            val intent = Intent(this, HowManyPlayers::class.java)
            startActivity(intent)
        }

        val setting = findViewById<Button>(R.id.settingsButton)
        setting.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        val addNewSongButton = findViewById<Button>(R.id.addNewSongButton)
        addNewSongButton.setOnClickListener {
            val intent = Intent(this, AddNewSong::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }




    //disable back from menu to start
    override fun finish() {
        minimizeApp()
    }

    fun minimizeApp() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

//    add hide navigation bar
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