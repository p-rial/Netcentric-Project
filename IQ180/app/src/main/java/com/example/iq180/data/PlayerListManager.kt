package com.example.iq180.data

import android.util.Log

class PlayerListManager {

    var playerList = ArrayList<Player>()

    fun getPositionOfPlayer(name: String): Int {
        Log.e("getPositionOfPlayer", name)
        for ((index, player) in playerList.withIndex()) {
            if (name == player.name) return index
        }
        return -1
    }

    override fun toString(): String {
        var text = ""
        for (player in playerList) {
            text += "[ ${player.name} ${player.socketId} ], "
        }
        return text
    }

    companion object {

        val instance: PlayerListManager by lazy {
            PlayerListManager()
        }
    }

}