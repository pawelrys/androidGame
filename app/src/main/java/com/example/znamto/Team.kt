package com.example.znamto

//Metoda Wytwórcza (Factory Method)
open class Team() {
    var points = 0
        get() = field
        set(value) {
            field = value
        }
}

public class Player(nick : String) : Team() {
    var nickPlayer = nick
        get() = field
}

public class Squad(count : Int, squad: Array<String>) : Team(){
    private var countSquad = count
        get() = field
    private var teamSquad : Array<String> = squad
        get() = field

    public fun getName (id : Int) : String{
        return teamSquad[id % countSquad]
    }
}