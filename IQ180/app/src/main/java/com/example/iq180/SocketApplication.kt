package com.example.iq180

import io.socket.client.IO
import io.socket.client.Socket

class SocketApplication private constructor() {



    companion object {
                private val mSocket: Socket = IO.socket("http://192.168.137.1:3000") // chula wifi 2
//        private val mSocket: Socket = IO.socket("http://192.168.2.11:3000") // condo hope
//        private val mSocket: Socket = IO.socket("http://192.168.43.193:3000") // top wifi
//        private val mSocket: Socket = IO.socket("http://192.168.1.53:3000") // condo wifi
//        private val mSocket: Socket = IO.socket("http://192.168.2.24:3000")


        val instance: SocketApplication by lazy {
            SocketApplication()
        }

    }

    fun getSocket(): Socket {
        return mSocket
    }

}