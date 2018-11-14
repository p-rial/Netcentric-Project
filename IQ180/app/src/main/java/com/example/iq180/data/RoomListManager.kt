package com.example.iq180.data

import android.util.Log

class RoomListManager {

    var roomList = ArrayList<Room>()

    fun getPositionOfRoom(hostName: String): Int {
        Log.e("getPositionOfRoom", hostName)
        for ((index, room) in roomList.withIndex()) {
            if (hostName == room.hostName) return index
        }
        return -1
    }

    override fun toString(): String {
        var text = ""
        for (room in roomList) {
            text += "[ ${room.roomName} ${room.hostName} ], "
        }
        return text
    }

    companion object {

        val instance: RoomListManager by lazy {
            RoomListManager()
        }
    }
}