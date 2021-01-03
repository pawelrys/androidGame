package com.example.znamto

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context) : SQLiteOpenHelper(context, "MYDATABASE", null, 1) {
    private val tableNameSongs = "SongsTable"
    private val tableNameAuthor = "AuthorTable"
    private val tableNameCategory = "CategoryTable"
    private val tableNameLanguage = "LanguageTable"
    private val idSongs = "IDSongs"
    private val idAuthor = "IDAuthor"
    private val idCategory = "IDCategory"
    private val idLanguage = "IDLanguage"
    private val titleTable = "Title"
    private val authorTable = "Author"
    private val categoryTable = "Category"
    private val languageTable = "Language"
    private val checkTable = "isChecked"
    private var instance: DataBase? = null

    //Singleton
    fun getInstance(context: Context): DataBase {
        if(instance == null){
            instance = DataBase(context)
        }
        return instance as DataBase
    }

    override fun onCreate(db: SQLiteDatabase) {
        val tableOfSongs = ("CREATE TABLE " +
                tableNameSongs + "("
                + idSongs + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + titleTable + " TEXT,"
                + authorTable + " INTEGER,"
                + categoryTable + " INTEGER,"
                + languageTable + " INTEGER,"
                + checkTable + " BOOLEAN DEFAULT TRUE,"
                + "FOREIGN KEY (" + authorTable + ") REFERENCES " + tableNameAuthor + " (" + idAuthor + "),"
                + "FOREIGN KEY (" + categoryTable + ") REFERENCES " + tableNameCategory + " (" + idCategory + "),"
                + "FOREIGN KEY (" + languageTable + ") REFERENCES " + tableNameLanguage + " (" + idLanguage + "));")
        db.execSQL(tableOfSongs)

        val tableOfAuthor = ("CREATE TABLE " +
                tableNameAuthor + "("
                + idAuthor + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + authorTable + " TEXT,"
                + checkTable + " BOOLEAN DEFAULT TRUE)")
        db.execSQL(tableOfAuthor)

        val tableOfCategory = ("CREATE TABLE " +
                tableNameCategory + "("
                + idCategory + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + categoryTable + " TEXT,"
                + checkTable + " BOOLEAN DEFAULT TRUE)")
                db.execSQL(tableOfCategory)

        val tableOfLanguage = ("CREATE TABLE " +
                tableNameLanguage + "("
                + idLanguage + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + languageTable + " TEXT,"
                + checkTable + " BOOLEAN DEFAULT TRUE)")
        db.execSQL(tableOfLanguage)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tableNameSongs")
        db.execSQL("DROP TABLE IF EXISTS $tableNameAuthor")
        db.execSQL("DROP TABLE IF EXISTS $tableNameCategory")
        db.execSQL("DROP TABLE IF EXISTS $tableNameLanguage")
        onCreate(db)
    }

    fun addSong(song : Song) {
        val values = ContentValues()

        val authorId = addAndGetAuthorId(song.authorSong)
        val categoryId = addAndGetCategoryId(song.categorySong)
        val languageId = addAndGetLanguageId(song.languageSong)

        val db = writableDatabase
        values.put(titleTable, song.titleSong)
        values.put(authorTable, authorId)
        values.put(categoryTable, categoryId)
        values.put(languageTable, languageId)
        db.insert(tableNameSongs, null, values)
        db.close()
    }

    fun getManyRandomSong(howManySong : Int) : ArrayList<Song> {
        val allSongs = getAllCheckedSongs()
        if(allSongs.count < howManySong) {
            return ArrayList<Song>()
        }
        val numberArray = ArrayList<Int>()
        val songsArray : ArrayList<Song> = ArrayList<Song>()
        while (numberArray.count() != howManySong) {
            val number = (0 until (allSongs.count)).random()
            if(!numberArray.contains(number)) {
                numberArray.add(number)
                allSongs.moveToPosition(number)
                val song = Song(allSongs.getString(0),Adapter.createAuthor(allSongs.getString(1)),allSongs.getString(2),allSongs.getString(3))
                songsArray.add(song)
            }
        }
        return songsArray
    }




    fun getAllSongs(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT * FROM $tableNameSongs", null)
    }

    private fun getAllCheckedSongs(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT $tableNameSongs.$titleTable, $tableNameAuthor.$authorTable, $tableNameCategory.$categoryTable, $tableNameLanguage.$languageTable FROM $tableNameSongs, $tableNameAuthor, $tableNameCategory, $tableNameLanguage WHERE $tableNameSongs.$authorTable = $tableNameAuthor.$idAuthor AND $tableNameSongs.$categoryTable = $tableNameCategory.$idCategory AND $tableNameSongs.$languageTable = $tableNameLanguage.$idLanguage AND $tableNameSongs.$checkTable = 'TRUE' AND $tableNameAuthor.$checkTable = 'TRUE' AND $tableNameCategory.$checkTable = 'TRUE' AND $tableNameLanguage.$checkTable = 'TRUE'", null)
    }

    fun getAllAuthor(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT * FROM $tableNameAuthor", null)
    }

    fun getAllCategory(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT * FROM $tableNameCategory", null)
    }

    fun getAllLanguage(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT * FROM $tableNameLanguage", null)
    }

    fun getAllTitle(): Cursor {
        val db = writableDatabase
        return db.rawQuery("SELECT $tableNameSongs.$idSongs, $tableNameSongs.$titleTable, $tableNameAuthor.$authorTable, $tableNameSongs.$checkTable FROM $tableNameSongs, $tableNameAuthor WHERE $tableNameSongs.$authorTable = $tableNameAuthor.$idAuthor", null)
    }

    fun updateCheck(id : Int, tableName : String, check : String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(checkTable, check)
        var columnID : String
        if(tableName == tableNameLanguage){
            columnID = idLanguage
        } else if(tableName == tableNameCategory){
            columnID = idCategory
        } else if(tableName == tableNameAuthor){
            columnID = idAuthor
        } else {
            columnID = idSongs
        }
        val idToChange = "$columnID=${id.toString()}"
        val success1 = db.update(tableName, values, idToChange, arrayOf());
    }

    fun getCount() : Int {
        val count = getAllSongs()
        return count.count
    }

    fun addAndGetAuthorId(author : Author) : Int {
        val authors = getAllAuthor()
        while (authors.moveToNext()) {
            if(authors.getString(1) == author.getAuthor()) return authors.getInt(0)
        }
        return addNewAuthor(author)
    }

    fun addNewAuthor(author : Author) : Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(authorTable, author.getAuthor())
        val id = db.insert(tableNameAuthor, null, values)
        db.close()
        return id.toInt()
    }

    fun addAndGetCategoryId(category: String) : Int {
        val categories = getAllCategory()
        while (categories.moveToNext()) {
            if(categories.getString(1) == category) return categories.getInt(0)
        }
        return addNewCategory(category)
    }

    fun addNewCategory(category : String) : Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(categoryTable, category)
        val id = db.insert(tableNameCategory, null, values)
        db.close()
        return id.toInt()
    }

    fun addAndGetLanguageId(language : String) : Int {
        val languages = getAllLanguage()
        while (languages.moveToNext()) {
            if(languages.getString(1) == language) return languages.getInt(0)
        }
        return addNewLanguage(language)
    }

    fun addNewLanguage(language : String) : Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(languageTable, language)
        val id = db.insert(tableNameLanguage, null, values)
        db.close()
        return id.toInt()
    }

    fun getAuthor(id : Int) : String{
        val authors = getAllAuthor()
        authors.moveToPosition(id - 1)
        return authors.getString(1)
    }

    fun getCategory(id : Int) : String{
        val category = getAllCategory()
        category.moveToPosition(id - 1)
        return category.getString(1)
    }

    fun getLanguage(id : Int) : String{
        val language = getAllLanguage()
        language.moveToPosition(id - 1)
        return language.getString(1)
    }
}

