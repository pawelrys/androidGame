package com.example.znamto

//Kompozyt
class Composite {
    val Teams = ArrayList<Player>()

    fun add(team : Player) {
        this.Teams.add(team)
    }

    fun isExist(team : Player) : Int {
        var idx = 0
        while(idx != Teams.count()) {
            if(Teams[idx++].nickPlayer == team.nickPlayer) {
                return 1
            }
        }
        return 0
    }

    fun getPoints(id : Int) : Int {
        return Teams[id].points
    }
}