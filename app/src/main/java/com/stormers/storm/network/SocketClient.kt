package com.stormers.storm.network

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import java.net.URISyntaxException


object SocketClient {
    private const val TAG = "SocketClient"

    const val JOIN_ROOM = "joinRoom"
    const val ROUND_COMPLETE = "roundComplete"
    const val LEAVE_ROOM = "leaveRoom"
    const val ROUND_START_HOST = "roundStartHost"
    const val ROUND_START_MEMBER = "roundStartMember"
    const val PREPARE_NEXT_ROUND = "prepareNextRound"
    const val WAIT_NEXT_ROUND = "waitNextRound"
    const val NEXT_ROUND = "nextRound"
    const val MEMBER_NEXT_ROUND = "memberNextRound"
    const val ENTER_NEXT_ROUND = "enterNextRound"
    const val FINISH_PROJECT = "finishProject"
    const val MEMBER_FINISH_PROJECT = "memberFinishProject"

    private const val SERVER_URL = "http://3.34.179.75:3000"
    private var socket: Socket? = null

    fun getInstance() : Socket? {
        if (socket == null) {
            socket = init()
        }

        return socket
    }
    fun connection(){
        socket?.connect()
        Log.d(TAG, "[socket] connect")
    }

    fun disconnection() {
        socket?.disconnect()
    }

    fun close() {
        socket?.close()
    }

    fun disconnectionAndClose() {
        disconnection()
        close()
        Log.d(TAG, "[socket] disconnect and close")
    }

    private fun init(): Socket? {
        if (socket == null) {
            try {
                socket = IO.socket(SERVER_URL)
                Log.d(TAG, "Initializing is done")
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }
        return socket
    }

    fun responseEvent(event: String, callback: Emitter.Listener) {
        if (init() != null) {
            socket!!.on(event, callback)
            Log.d(TAG, "responseEvent: $event")
        } else {
            Log.d(TAG, "initial is failed")
        }
    }

    fun sendEvent(event: String, data: String) : Boolean {
        return if (init() != null) {
            try {
                socket!!.emit(event, data)
                Log.d(TAG, "sendEvent: $event, data: $data")
                true
            } catch (e: JSONException) {
                e.printStackTrace()
                false
            }
        } else {
            Log.d(TAG, "initial is failed")
            false
        }
    }

    fun offEvent(event: String) {
        if (init() != null) {
            socket!!.off(event)
            Log.d(TAG, "offEvent: $event")
        } else {
            Log.d(TAG, "initial is failed")
        }
    }
}