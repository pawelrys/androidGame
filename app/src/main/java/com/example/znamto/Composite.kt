package com.example.znamto

//Kompozyt
class Composite {
    val Teams = ArrayList<Team>()

    public fun add(team : Team){
        this.Teams.add(team)
    }

    public fun getPoints(id : Int) : Int{
        return Teams[id].points
    }
}