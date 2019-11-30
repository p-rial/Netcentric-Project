package com.example.iq180.lobby

import android.support.v4.app.FragmentActivity

interface LobbyContract {

    interface View {
        fun getmActivity(): FragmentActivity?
        fun refreshPlayerList(state: Int, index: Int)
        fun refreshRoomList(state: Int, index: Int)
        fun startGameActivity(name: String, name2: String, roomName: String)
        fun dismissDialog()
        fun showToast(msg: String)

    }
    interface Presenter {
        fun connectToServer(name: String)
        fun disconnectServer()
        fun createRoomAndWaiting(playerName: String, roomName: String)
        fun abortRoom()
        fun joinRoom(hostName: String, roomName: String)
    }

}