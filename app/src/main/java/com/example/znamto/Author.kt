package com.example.znamto

//Metoda Wytwórcza (Factory Method)
interface Author {
    fun getAuthor() : String
}

//Dependency Injection (iniekcja zależności) - 9 linijka
class Singer(name : String, private val surnameSinger: String) : Author {
    private val nameSinger : String = name

    override fun getAuthor(): String {
        return nameSinger + surnameSinger
    }
}

class Band(name : String) : Author {
    private val nameBand : String = name

    override fun getAuthor(): String {
        return nameBand
    }
}

//Adapter
class Adapter {
    companion object {
        fun createAuthor(author : String) : Author {
            val idx = author.indexOf(' ')
            if(idx != -1) {
                return Singer(author.substring(0, idx), author.substring(idx))
            } else {
                return Band(author)
            }
        }

        fun getAuthor(author : Author) : String {
            return author.getAuthor()
        }
    }
}





