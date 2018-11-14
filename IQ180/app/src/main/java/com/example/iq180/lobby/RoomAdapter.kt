package com.example.iq180.lobby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iq180.R
import com.example.iq180.data.RoomListManager
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private val roomListManager = RoomListManager.instance
    private val roomList = roomListManager.roomList

    private lateinit var onRoomClickListener: OnRoomClickListener

    fun setOnRoomClickListener(onRoomClickListener: OnRoomClickListener) {
        this.onRoomClickListener = onRoomClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_room, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.bindView(roomList[pos].hostName, roomList[pos].roomName)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(name: String, roomName: String) {
            with(itemView) {
                tvHostName.text = name
                tvRoomName.text = roomName

                list_item.setOnClickListener { onRoomClickListener.joinRoom(name, roomName) }
            }
        }
    }

    interface OnRoomClickListener {
        fun joinRoom(hostName: String, roomName: String)
    }
}