package com.stormers.storm.network

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


object SocketClient {
    private val TAG = javaClass.name

    //Todo: URL 받으면 추가하기
    private const val SERVER_URL = ""

    private var socket: Socket? = null

    fun getInstance() : Socket? {
        if (socket == null) {
            socket = init()
        }

        return socket
    }

    private fun init(): Socket? {
        if (socket == null) {
            try {
                socket = IO.socket(SERVER_URL)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }

        Log.d(TAG, socket.toString())

        return socket
    }

    fun responseEvent(event: String, callback: Emitter.Listener) {
        if (init() != null) {
            socket!!.on(event, callback)
        } else {
            Log.d(TAG, "initial is failed")
        }
    }

    fun sendEvent(event: String, data: JSONObject) : Boolean {
        return if (init() != null) {
            try {
                socket!!.emit(event, data)
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
}