package com.example.znamto

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.start_play.*


@Suppress("DEPRECATION")
class StartPlay : AppCompatActivity() {
    companion object {
        var timeToSong : Long = 8000
        var howManySongsForRound = 10
        var knowSongs : Int = 0
        var allSongs : Int = 0

        var composite = Composite()
        var playWithFriends = false
        var howManyPlayers = 0
        var nowPlay = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_play)


        val songsToShow = randomSongs()
        val iterator = songsToShow.createIterator()
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        val startPlayText = findViewById<TextView>(R.id.startPlayText)

        if(playWithFriends == false) {
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
                            knowSongs = 0
                            allSongs = 0
                            showSongWithoutFriends(iterator)
                            Handler().postDelayed({
                            }, 500)
                        }, 1000)
                    }, 1000)
                }, 1000)
            }, 1000)
        } else {
            Handler().postDelayed({
                startPlayText.text = "Teraz zgaduje ${composite.Teams[nowPlay].nickPlayer}!"
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
                                knowSongs = 0
                                allSongs = 0
                                showSongWithFriends(iterator)
                                Handler().postDelayed({
                                }, 500)
                            }, 1000)
                        }, 1000)
                    }, 1000)
                }, 2000)
            }, 1000)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MenuClass::class.java)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        startActivity(intent)
    }

    fun randomSongs() : SongsListCollection {
        val db = DataBase(this).getInstance(this)
        val songs = SongsListCollection(db.getManyRandomSong(howManySongsForRound))
        return songs
    }

    fun showSongWithoutFriends(iterator : SongsListCollection.ListIterator) {
        if(!iterator.isNull()) {
            val song = iterator.next()
            val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
            val startPlayText = findViewById<TextView>(R.id.startPlayText)
            val startPlayAuthorText = findViewById<TextView>(R.id.startPlayAuthorText)
            startPlayText.text = song.titleSong
            startPlayText.startAnimation(animation)
            startPlayAuthorText.text = Adapter.getAuthor(song.authorSong)
            startPlayAuthorText.startAnimation(animation)
            val howManySong = findViewById<TextView>(R.id.howManyKnowSong)
            val know = findViewById<LinearLayout>(R.id.know)
            val dontKnow = findViewById<LinearLayout>(R.id.dontKnow)

            val timer: CountDownTimer = object : CountDownTimer(timeToSong, 1000) {
                override fun onFinish() {
                    allSongs++
                    howManySong.text = ("$knowSongs/$allSongs")
                    cancel()
                    Handler().postDelayed({
                        showSongWithoutFriends(iterator)
                    }, 500)
                }

                override fun onTick(millisUntilFinished: Long) {
                    startPlayTimer.text = (millisUntilFinished / 1000).toString()
                }
            }.start()

            know.clickWithDebounce {
                knowSongs++
                startPlayText.text = "Brawo!"
                timer.onFinish()
            }

            dontKnow.clickWithDebounce {
                startPlayText.text = "Następnym razem się uda!"
                timer.onFinish()
            }
        }
        else {
            val intent = Intent(this, MenuClass::class.java)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            startActivity(intent)
        }
    }


    fun showSongWithFriends(iterator : SongsListCollection.ListIterator) {
        if(!iterator.isNull()) {
            val song = iterator.next()
            val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
            val startPlayText = findViewById<TextView>(R.id.startPlayText)
            val startPlayAuthorText = findViewById<TextView>(R.id.startPlayAuthorText)
            startPlayText.text = song.titleSong
            startPlayText.startAnimation(animation)
            startPlayAuthorText.text = Adapter.getAuthor(song.authorSong)
            startPlayAuthorText.startAnimation(animation)
            val howManySong = findViewById<TextView>(R.id.howManyKnowSong)
            val know = findViewById<LinearLayout>(R.id.know)
            val dontKnow = findViewById<LinearLayout>(R.id.dontKnow)

            val timer: CountDownTimer = object : CountDownTimer(timeToSong, 1000) {
                override fun onFinish() {
                    allSongs++
                    howManySong.text = ("$knowSongs/$allSongs")
                    cancel()
                    Handler().postDelayed({
                        showSongWithFriends(iterator)
                    }, 500)
                }

                override fun onTick(millisUntilFinished: Long) {
                    startPlayTimer.text = (millisUntilFinished / 1000).toString()
                }
            }.start()

            know.clickWithDebounce {
                knowSongs++
                startPlayText.text = "Brawo!"
                timer.onFinish()
            }

            dontKnow.clickWithDebounce {
                startPlayText.text = "Następnym razem się uda!"
                timer.onFinish()
            }
        }
        else {
            allSongs = 0
            knowSongs = 0
            nowPlay += 1
            nowPlay %= howManyPlayers
            howManyPlayers = TypePlayerName.howManyPlayers
            playWithFriends = true
            val intent = Intent(this, StartPlay::class.java)
            startActivity(intent)
        }
    }


    fun View.clickWithDebounce(debounceTime: Long = 1000L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()

                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }









//    //add hide navigation bar
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