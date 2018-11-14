package com.example.iq180.game

import android.support.v4.app.FragmentActivity

interface GameContract {

    interface View {
        fun getmActivity(): FragmentActivity?
        fun setGameEnvironment(arrNum: ArrayList<String>, result: Int)
        fun showWaiting()
        fun hideWaiting(opponentTimeLeft: String)
        fun showRoundResult(winner: String, score1: Int, score2: Int)
        fun showToast(msg: String)
        fun leaveGame()
        fun setScore(score1: Int, score2: Int)
    }

    interface Presenter {
        fun loadGameInfo(roomName: String, hostName: String = "")
        fun loadNewRoundInfo(roomName: String, isRematch: Boolean)
        fun quitGame(roomName: String)
        fun changeTurnNotAnswer(roomName: String)
        fun changeTurnAnswer(roomName: String, time: String)
        fun sendRoundResult(roomName: String, winner: String)
    }
}