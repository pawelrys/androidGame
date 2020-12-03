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


@Suppress("DEPRECATION")
class startClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        addStartSongs()
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
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                startActivity(intent)
            }, 20)
        }, 20)


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

    fun addStartSongs(){
        val a1 = Song("Wulkan", "Volver", "POP", "Polski")
        val a2 = Song("Chce", "Volver", "POP", "Polski")
        val a3 = Song("Każda Noc, Każdy Dzień", "Volver", "POP", "Polski")
        val a4 = Song("Z tobą mam sen", "Volver", "POP", "Polski")
        val a5 = Song("Idę na plaże", "Video", "POP", "Polski")
        val a6 = Song("Fantastyczny Lot", "Video", "POP", "Polski")
        val a7 = Song("Środa Czwartek", "Video", "POP", "Polski")
        val a8 = Song("To Twoja Win", "Video", "POP", "Polski")
        val a9 = Song("Dwie Bajki", "Doda", "POP", "Polski")
        val a10 = Song("Dżaga", "Doda", "POP", "Polski")
        val a11 = Song("Nie daj się", "Doda", "POP", "Polski")
        val a12 = Song("Chodź przytul przebacz", "Andrzej Piaseczny", "POP", "Polski")
        val a13 = Song("Z Dwojga ciał", "Andrzej Piaseczny", "POP", "Polski")
        val a14 = Song("Moc", "Gosia Andrzejewicz", "POP", "Polski")
        val a15 = Song("Doceń to", "Gosia Andrzejewicz", "POP", "Polski")
        val a16 = Song("Obiecaj Mi", "Gosia Andrzejewicz", "POP", "Polski")
        val a17 = Song("Pozwól Żyć", "Gosia Andrzejewicz", "POP", "Polski")
        val a18 = Song("Siła Marzeń", "Gosia Andrzejewicz", "POP", "Polski")
        val a19 = Song("Karuzela", "Sylwia Grzeszczak", "POP", "Polski")
        val a20 = Song("Pożyczony", "Sylwia Grzeszczak", "POP", "Polski")
        val a21 = Song("Mijamy się", "Sylwia Grzeszczak", "POP", "Polski")
        val a22 = Song("Sen o przeszłości", "Sylwia Grzeszczak", "POP", "Polski")

        var db : DataBase = DataBase(this).getInstance(this)

        db.addSong(a1)
        db.addSong(a2)
        db.addSong(a3)
        db.addSong(a4)
        db.addSong(a5)
        db.addSong(a6)
        db.addSong(a7)
        db.addSong(a8)
        db.addSong(a9)
        db.addSong(a10)
        db.addSong(a11)
        db.addSong(a12)
        db.addSong(a13)
        db.addSong(a14)
        db.addSong(a15)
        db.addSong(a16)
        db.addSong(a17)
        db.addSong(a18)
        db.addSong(a19)
        db.addSong(a20)
        db.addSong(a21)
        db.addSong(a22)
    }
}