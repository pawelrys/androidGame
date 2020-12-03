package com.example.znamto

//Prototyp
open class Song(title: String, author: String, category: String, language: String) : Cloneable{
    var titleSong:String? = title
        get() = field
    var authorSong:String? = author
        get() = field
    var categorySong:String? = category
        get() = field
    var languageSong:String? = language
        get() = field

    private fun Song(): Song {
        return this
    }

    public override fun clone(): Song {
        return Song()
    }

    fun changeLanguage(language: String){
        languageSong = "$language From $languageSong"
    }
}

//Iterator
interface Iterator{
    fun hasNext() : Boolean
    fun next() : Song
}

class SongsListCollection {
    private val songs = ArrayList<Song>()

    fun add(song : Song){
        songs.add(song)
    }

    fun createIterator() = ListIterator(this)

    class ListIterator(private val collection : SongsListCollection) : Iterator {
        private var index = 0

        override fun hasNext(): Boolean {
            return index < collection.songs.size
        }

        override fun next(): Song {
            return collection.songs[index++]
        }

        fun isNull() : Boolean {
            return index >= collection.songs.size
        }
    }
}