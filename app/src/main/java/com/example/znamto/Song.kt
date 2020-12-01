package com.example.znamto

//Prototyp
open class Song(id: Int, title: String, author: String, category: String, language: String) : Cloneable{
    private var idSong:Int = id
    private var titleSong:String? = title
    private var authorSong:String? = author
    private var categorySong:String? = category
    private var languageSong:String? = language

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
    }
}