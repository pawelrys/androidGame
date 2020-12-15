package com.example.znamto

//Prototyp
//Most (Bridge) (Song posiada Authora)
open class Song(title: String, author: Author, category: String, language: String) : Cloneable{
    var titleSong:String = title
        get() = field
    var authorSong:Author = author
        get() = field
    var categorySong:String = category
        get() = field
    var languageSong:String = language
        get() = field

    private fun Song(): Song {
        return this
    }

    fun getTitle() : String {
        return titleSong
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

class SongsListCollection (song : ArrayList<Song> = ArrayList<Song>()){
    private val songs = song

    fun add(song : Song) {
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