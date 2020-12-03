package com.example.znamto

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.random.Random

class DataBase(context: Context) : SQLiteOpenHelper(context, "MYDATABASE", null, 1) {
    val tableName = "Songs"
    val id = "ID"
    val titleTable = "Title"
    val authorTable = "Author"
    val categoryTable = "Category"
    val languageTable = "Language"
    var instance: DataBase? = null

    //Singleton
    fun getInstance(context: Context): DataBase {
        if(instance == null){
            instance = DataBase(context)
        }
        return instance as DataBase
    }

    override fun onCreate(db: SQLiteDatabase) {
        val tableOfSongs = ("CREATE TABLE " +
                tableName + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + titleTable + " TEXT,"
                + authorTable + " TEXT,"
                + categoryTable + " TEXT,"
                + languageTable + " TEXT" + ")")
        db.execSQL(tableOfSongs)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun addSong(song : Song) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(titleTable, song.titleSong)
        values.put(authorTable, song.authorSong)
        values.put(categoryTable, song.categorySong)
        values.put(languageTable, song.languageSong)
        db.insert(tableName, null, values)
        db.close()
    }

    fun getRandomSong() : Song {
        val allSongs = getAllSongs()
        allSongs.moveToPosition((0 until (allSongs.count)).random())
        return Song(allSongs.getString(1),allSongs.getString(2),allSongs.getString(3),allSongs.getString(4))
    }

    public fun getAllSongs(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT * FROM $tableName", null)
    }
}



















//    fun insertData(user: User) {
//        val database = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(COL_NAME, user.name)
//        contentValues.put(COL_AGE, user.age)
//        val result = database.insert(TABLENAME, null, contentValues)
//        if (result == (0).toLong()) {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//        }
//        else {
//            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//        }
//    }
//    fun readData(): MutableList<User> {
//        val list: MutableList<User> = ArrayList()
//        val db = this.readableDatabase
//        val query = "Select * from $TABLENAME"
//        val result = db.rawQuery(query, null)
//        if (result.moveToFirst()) {
//            do {
//                val user = User()
//                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
//                user.name = result.getString(result.getColumnIndex(COL_NAME))
//                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
//                list.add(user)
//            }
//            while (result.moveToNext())
//        }
//        return list
//    }