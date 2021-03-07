package com.k9rosie.rpggame.net

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers

class SocketBuilder(actorSelectorManager: ActorSelectorManager = ActorSelectorManager(Dispatchers.IO)) {
    private val socketBuilder = aSocket(actorSelectorManager).tcp()
    lateinit var socket: ServerSocket

    fun build(hostname: String, port: Int) {
        socket = socketBuilder.bind(hostname, port)
    }
}