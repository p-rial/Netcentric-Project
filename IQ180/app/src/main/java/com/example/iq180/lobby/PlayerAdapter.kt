package com.example.iq180.lobby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iq180.R
import com.example.iq180.data.PlayerListManager
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter() : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private val playerListManager = PlayerListManager.instance
    private val playerList = playerListManager.playerList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_player, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.bindView(playerList[pos].name)
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(name: String ) {
            itemView.tvPlayerName.text = name
        }
    }
}
