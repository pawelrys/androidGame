package com.example.znamto

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class AllSongsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_songs_page)

        var table: TableLayout = findViewById(R.id.tableSongs)

        val db = DataBase(this).getInstance(this)

        val allSongs = db.getAllSongs()

        while (allSongs.moveToNext()) {
            val newRow = TableRow(this)

            val layoutParams = TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(1, 1, 1, 1)
            newRow.layoutParams = layoutParams

            val textSong : TextView = TextView(this)
            textSong.text = allSongs.getString(1)
            textSong.setTextColor(Color.BLACK)
            textSong.setBackgroundColor(Color.WHITE)
            textSong.gravity = Gravity.CENTER
            textSong.height = 80
            newRow.addView(textSong, layoutParams)

            val textAuthor : TextView = TextView(this)
            textAuthor.text = db.getAuthor(allSongs.getInt(2))
            textAuthor.setTextColor(Color.BLACK)
            textAuthor.setBackgroundColor(Color.WHITE)
            textAuthor.gravity = Gravity.CENTER
            textAuthor.height = 80
            newRow.addView(textAuthor, layoutParams)

            val textCategory : TextView = TextView(this)
            textCategory.text = db.getCategory(allSongs.getInt(3))
            textCategory.setTextColor(Color.BLACK)
            textCategory.setBackgroundColor(Color.WHITE)
            textCategory.gravity = Gravity.CENTER
            textCategory.height = 80
            newRow.addView(textCategory, layoutParams)

            val textLanguage : TextView = TextView(this)
            textLanguage.text = db.getLanguage(allSongs.getInt(4))
            textLanguage.setTextColor(Color.BLACK)
            textLanguage.setBackgroundColor(Color.WHITE)
            textLanguage.gravity = Gravity.CENTER
            textLanguage.height = 80
            newRow.addView(textLanguage, layoutParams)

            table.addView(
                newRow,
                TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
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
