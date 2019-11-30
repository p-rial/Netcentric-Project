package com.example.iq180.lobby

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.iq180.R
import com.example.iq180.data.Player
import com.example.iq180.di.Injection
import com.example.iq180.dialog.CreateRoomDialog
import com.example.iq180.game.GameActivity
import kotlinx.android.synthetic.main.fragment_lobby.*

class LobbyFragment : Fragment(), LobbyContract.View {


    private lateinit var presenter: LobbyContract.Presenter

    private lateinit var roomAdapter: RoomAdapter
    private lateinit var playerAdapter: PlayerAdapter

    private lateinit var alertDialog: AlertDialog

    private val SINGLE = 1
    private val MULTIPLAY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Injection.getLobbyPresenter(this)
        if (savedInstanceState == null) {
            onRestoreInstanceState(arguments)
        } else {
            onRestoreInstanceState(savedInstanceState)
        }

        presenter.connectToServer(player.name)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomAdapter = RoomAdapter()
        roomAdapter.setOnRoomClickListener(onRoomClickListener)
        playerAdapter = PlayerAdapter()

        recyclerViewRoom.layoutManager = LinearLayoutManager(context)
        recyclerViewRoom.adapter = roomAdapter

        recyclerViewPlayer.layoutManager = LinearLayoutManager(context)
        recyclerViewPlayer.adapter = playerAdapter

        lobbyBtnCreate.setOnClickListener {
            createDialog()
        }

        lobbyBtnSingle.setOnClickListener {
            val intent = Intent(activity, GameActivity::class.java)
            intent.putExtra("name", player.name)
            intent.putExtra("name2", "")
            intent.putExtra("roomName", "")
            intent.putExtra("requestCode", SINGLE)
            startActivity(intent)
        }
    }

    private fun createDialog() {
        val createRoomDialog = CreateRoomDialog.Builder()
                .setBtnName("CONFIRM")
                .build()
        createRoomDialog.show(childFragmentManager, "CreateRoomDialog")
        createRoomDialog.setOnDialogListener(object : CreateRoomDialog.OnDialogListener {
            override fun onPositiveBtnClicked(roomName: String) {
                Toast.makeText(context, roomName, Toast.LENGTH_SHORT).show()

                presenter.createRoomAndWaiting(player.name, roomName)

                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Waiting for another player join your room....")
                        .setCancelable(false)
                        .setNegativeButton("Cancel", { dialog, _ ->
                                dialog.dismiss()
                                presenter.abortRoom()
                        })
                alertDialog = alertDialogBuilder.create()
                alertDialog.show()

                createRoomDialog.dismiss()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disconnectServer()
    }

    override fun getmActivity(): FragmentActivity? {
        return activity
    }

    override fun refreshPlayerList(state : Int, index: Int) {
        when (state) {
            0 -> playerAdapter.notifyDataSetChanged()
            1 -> playerAdapter.notifyItemRemoved(index)
        }
    }

    override fun refreshRoomList(state: Int, index: Int) {
        when (state) {
            0 -> roomAdapter.notifyDataSetChanged()
            1 -> roomAdapter.notifyItemRemoved(index)
        }

    }

    override fun dismissDialog() {
        alertDialog.dismiss()
    }

    override fun startGameActivity(name: String, name2: String, roomName: String) {
        val intent = Intent(activity, GameActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("name2", name2)
        intent.putExtra("roomName", roomName)
        intent.putExtra("requestCode", MULTIPLAY)
        startActivity(intent)
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("player", player)
    }

    private fun onRestoreInstanceState(arguments: Bundle?) {
        player = arguments!!.getSerializable("player") as Player
    }

    /**
     *
     * Listener
     *
     */

    private val onRoomClickListener = object : RoomAdapter.OnRoomClickListener {

        override fun joinRoom(hostName: String, roomName: String) {
            presenter.joinRoom(hostName, roomName)
        }

    }


    companion object {

        private val lobbyFragment = LobbyFragment()
        lateinit var player: Player

        fun newInstance(name: String): LobbyFragment {
            player = Player(name)
            val arg = Bundle()
            arg.putSerializable("player", player)
            lobbyFragment.arguments = arg
            return lobbyFragment
        }

        fun getInstance(): LobbyFragment {
            return lobbyFragment
        }
    }
}