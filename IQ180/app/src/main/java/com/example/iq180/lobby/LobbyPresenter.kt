package com.example.iq180.lobby

import android.util.Log
import com.example.iq180.SocketApplication
import com.example.iq180.data.Player
import com.example.iq180.data.PlayerListManager
import com.example.iq180.data.Room
import com.example.iq180.data.RoomListManager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONObject

class LobbyPresenter(
        private val lobbyFragment: LobbyContract.View
) : LobbyContract.Presenter {

    private val ADD_PLAYER = 0
    private val REMOVE_PLAYER = 1

    private val ADD_ROOM = 0
    private val REMOVE_ROOM = 1

    private lateinit var mSocket: Socket

    private val playerListManager = PlayerListManager.instance
    private val playerList = playerListManager.playerList
    private val roomListManager = RoomListManager.instance
    private val roomList = roomListManager.roomList

    private val mActivity = lobbyFragment.getmActivity()

    override fun connectToServer(name: String) {

        mSocket = SocketApplication.instance.getSocket()

        mSocket.on("socket", onSocket)
        mSocket.on("online player", onPlayerList)
        mSocket.on("online room", onRoomList)
        mSocket.on("new user connect", onNewUserConnect)
        mSocket.on("user disconnected", onUserDisconnect)
        mSocket.on("new room created", onNewRoomCreate)
        mSocket.on("room disconnected", onRoomDisconnect)
        mSocket.on("start game", onStartGame)
        mSocket.connect()
        mSocket.emit("user connect", name)
    }

    override fun disconnectServer() {
        playerList.clear()
        roomList.clear()
        mSocket.disconnect()

        mSocket.off("online player", onPlayerList)
        mSocket.off("online room", onRoomList)
        mSocket.off("new user connect", onNewUserConnect)
        mSocket.off("user disconnected", onUserDisconnect)
        mSocket.off("socket", onSocket)
        mSocket.off("start game", onStartGame)
    }

    override fun createRoomAndWaiting(playerName: String, roomName: String) {
        val obj = JSONObject()
        obj.put("hostName", playerName)
        obj.put("roomName", roomName)
        mSocket.emit("create room", obj)
    }

    override fun abortRoom() {
        mSocket.emit("room aborted", LobbyFragment.player.name)
    }

    override fun joinRoom(hostName: String, roomName: String) {
        mSocket.emit("JOIN_ROOM", roomName, hostName, LobbyFragment.player.name)
        mSocket.emit("room aborted", hostName)
    }


    /****
     *
     *
     * Emitter Listener
     *
     */

    private val onSocket = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            val data = args[0] as String
            Log.e("onSocket", data)
            LobbyFragment.player.socketId = data
        }
    }

    private val onPlayerList = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            val data: JSONArray = args[0] as JSONArray

            Log.e("onPlayerList", data.toString())
            var len = data.length()
            for (i in 0 until len) {
                var obj = data.getJSONObject(i)
                playerList.add(Player(obj.getString("name"),
                        obj.getString("socketId")))
            }
            lobbyFragment.refreshPlayerList(ADD_PLAYER, -1)
        }
    }

    private val onRoomList = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            val data = args[0] as JSONArray
            Log.e("onRoomList", data.toString())

            var len = data.length()
            for (i in 0 until len) {
                var obj = data.getJSONObject(i)
                roomList.add(Room(obj.getString("hostName"),
                        obj.getString("roomName")))
            }
            lobbyFragment.refreshRoomList(ADD_ROOM, -1)
        }
    }

    private val onNewUserConnect = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var data = args[0] as JSONObject
            Log.e("onNewUserConnect", data.toString())
            playerList.add(Player(data.getString("name"),
                    data.getString("socketId")))
            lobbyFragment.refreshPlayerList(ADD_PLAYER, -1)
        }
    }

    private val onNewRoomCreate = Emitter.Listener { args ->
        mActivity?.runOnUiThread{
            var data = args[0] as JSONObject
            Log.e("onNewRoomCreate", data.toString())
            roomList.add(Room(data.getString("hostName"),
                    data.getString("roomName")))
            lobbyFragment.refreshRoomList(ADD_ROOM, -1)
        }
    }

    private val onUserDisconnect = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var data = args[0] as String
            Log.e("onUserDisconnect", data)
            var x = playerListManager.getPositionOfPlayer(data)
            playerList.removeAt(x)
            lobbyFragment.refreshPlayerList(REMOVE_PLAYER, x)
        }
    }

    private val onRoomDisconnect = Emitter.Listener { args ->
        mActivity?.runOnUiThread{
            val data = args[0] as String
            Log.e("onRoomDisconnect", data)
            var x = roomListManager.getPositionOfRoom(data)
            roomList.removeAt(x)
            lobbyFragment.refreshRoomList(REMOVE_ROOM, x)
        }
    }

    private val onStartGame = Emitter.Listener { args ->
        mActivity?.runOnUiThread {
            var data = args[0] as String
            var data2 = args[1] as String
            var data3 = args[2] as String
            lobbyFragment.showToast("$data $data2")
            if (data == LobbyFragment.player.name) lobbyFragment.dismissDialog()
            lobbyFragment.startGameActivity(data, data2, data3)
        }
    }


}