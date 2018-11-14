package com.example.iq180.di

import com.example.iq180.game.GameContract
import com.example.iq180.game.GamePresenter
import com.example.iq180.lobby.LobbyContract
import com.example.iq180.lobby.LobbyPresenter

object Injection {

    fun getLobbyPresenter(view: LobbyContract.View)
            : LobbyContract.Presenter {
        return LobbyPresenter(view)
    }

    fun getGamePresenter(view: GameContract.View)
            : GameContract.Presenter {
        return GamePresenter(view)
    }

}