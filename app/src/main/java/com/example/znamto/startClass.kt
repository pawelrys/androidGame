package com.example.znamto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.start_page.*

class startClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                startActivity(intent)
            }, 4000)
        }, 4000)

//        Handler().postDelayed({
//            val intent = Intent(this, startSecondClass::class.java)
//            startActivity(intent);
//        }, 3000);
//        button.setOnClickListener{
//            val intent = Intent(this, startSecondClass::class.java);
//            startActivity(intent);
//        }

    }
}