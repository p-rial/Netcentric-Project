package com.example.iq180.data

import java.io.Serializable

class Player(var name: String, var socketId: String? = null, var score: Int = 0, var turn: Int = -1) : Serializable {

}

