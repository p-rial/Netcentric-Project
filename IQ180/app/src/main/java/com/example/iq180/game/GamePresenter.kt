package com.example.iq180.game

import android.util.Log
import com.example.iq180.SocketApplication
import com.example.iq180.lobby.LobbyFragment
import io.socket.emitter.Emitter
import org.json.JSONArray

class GamePresenter(
        private val gameFragment: GameContract.View
) : GameContract.Presenter {


    private val mSocket = SocketApplication.instance.getSocket()

    private val mActivity = gameFragment.getmActivity()

    override fun loadGameInfo(roomName: String, hostName: String) {
        mSocket.on("round result", onRoundResult)
        mSocket.on("your turn", onYourTurn)
        mSocket.on("game info", onGameInfo)
        mSocket.on("leave room", onLeaveRoom)
        mSocket.on("reset score", onResetScore)
        Log.e("loadGameInfo", roomName)
        if (hostName == "" || hostName == LobbyFragment.player.name) {
            mSocket.emit("game start", roomName)
        }
    }

    override fun loadNewRoundInfo(roomName: String, isRematch: Boolean) {
        if (isRematch) mSocket.emit("reset score", roomName )
        mSocket.emit("game start", roomName)
    }

    override fun quitGame(roomName: String) {
        mSocket.emit("quit game", roomName)
    }

    override fun changeTurnNotAnswer(roomName: String) {
        mSocket.emit("change turn no answer", roomName)
        gameFragment.showWaiting()
    }

    override fun changeTurnAnswer(roomName: String, time: String) {
        mSocket.emit("change turn answer", roomName, time)
        gameFragment.showWaiting()
    }

    override fun sendRoundResult(roomName: String, winner: String) {
        mSocket.emit("round result", roomName, winner)
    }


    /***
     *
     * Emitter
     *
     */

    private val onGameInfo = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var data = args[0] as String
            var data2 = args[1] as Int
            var data3 = args[2] as JSONArray
            var turn = args[3] as String

            Log.e("data", data)
            Log.e("data2", data2.toString())
            Log.e("data3", data3.toString())

            var len = data3.length()
            var arrNum = ArrayList<String>()
            for (i in 0 until len) {
                var obj = data3.getInt(i)
                arrNum.add(obj.toString())
            }

            Log.e("onGameInfo", LobbyFragment.player.socketId)
            when (turn) {
                "" -> gameFragment.setGameEnvironment(arrNum, data2)
                LobbyFragment.player.socketId -> {
                    gameFragment.showToast("Play first")
                    LobbyFragment.player.turn = 1
                    gameFragment.setGameEnvironment(arrNum, data2)
                }
                else -> {
                    gameFragment.showToast("Play last")
                    LobbyFragment.player.turn = 2
                    gameFragment.setGameEnvironment(arrNum, data2)
                    gameFragment.showWaiting()

                }
            }
        }
    }

    private val onYourTurn = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var opponentTimeLeft = args[0] as String
            gameFragment.hideWaiting(opponentTimeLeft)

        }
    }

    private val onRoundResult = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var winner = args[0] as String
            var score1 = args[1] as Int
            var score2 = args[2] as Int

            Log.e("onRoundResult", "$winner $score1 $score2")

            gameFragment.showRoundResult(winner, score1, score2)
        }
    }

    private val onLeaveRoom = Emitter.Listener {
        mActivity?.runOnUiThread {
            gameFragment.leaveGame()
        }
    }

    private val onResetScore = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var score1 = args[0] as Int
            var score2 = args[1] as Int

            gameFragment.setScore(score1, score2)
        }
    }

}