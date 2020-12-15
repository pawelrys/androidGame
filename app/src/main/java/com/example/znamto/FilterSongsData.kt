package com.example.znamto

import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class FilterSongsData : AppCompatActivity() {
    companion object {
        var filterData : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        onWindowFocusChanged(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_data)


        val tableFilter = findViewById<TableLayout>(R.id.tableFilter)
        val db = DataBase(this).getInstance(this)
        var allData : Cursor = getAllData()


        var idBox : Int = 0
        while (allData.moveToNext()) {
            val newRow = TableRow(this)

            val layoutParams = TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            layoutParams.setMargins(1, 1, 1, 1)
            newRow.layoutParams = layoutParams

            val upperCasePrinter = Printer(upperCaseFormatter)
            val box = CheckBox(this)
            if(filterData == "SongsTable") {
                box.text = "\"" + allData.getString(1) + "\" " +  allData.getString(2)
                box.isChecked = upperCasePrinter.getModifyString(allData.getString(3)) == "TRUE"
            }
            else {
                box.text = allData.getString(1)
                box.isChecked = upperCasePrinter.getModifyString(allData.getString(2)) == "TRUE"

            }
            box.id = idBox++
            box.setTextColor(Color.BLACK)
            box.setBackgroundColor(Color.WHITE)
            box.gravity = Gravity.CENTER
            box.height = 140
            box.textSize = 20F
            newRow.addView(box, layoutParams)
            box.setOnClickListener {
                allData = getAllData()
                val idBoxes = box.id
                allData.moveToPosition(idBoxes)
                if(filterData == "SongsTable") {
                    if (allData.getString(3) == "TRUE") {
                        db.updateCheck(allData.getInt(0), filterData, "FALSE")
                    } else {
                        db.updateCheck(allData.getInt(0), filterData, "TRUE")
                    }
                }
                else if(filterData != "SongsTable" && allData.getString(2) == "TRUE") {
                    db.updateCheck(allData.getInt(0), filterData, "FALSE")
                }
                else {
                    db.updateCheck(allData.getInt(0), filterData, "TRUE")
                }

            }

            tableFilter.addView(
                newRow,
                TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }


    fun getAllData() : Cursor {
        val db = DataBase(this).getInstance(this)
        val allData : Cursor
        if(filterData == "AuthorTable") {
            allData = db.getAllAuthor()
        } else if(filterData == "LanguageTable"){
            allData = db.getAllLanguage()
        } else if(filterData == "CategoryTable"){
            allData = db.getAllCategory()
        } else {
            allData = db.getAllTitle()
        }
        return allData
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