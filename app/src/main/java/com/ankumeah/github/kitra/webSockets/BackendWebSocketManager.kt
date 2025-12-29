package com.ankumeah.github.kitra.webSockets

import android.util.Log
import com.ankumeah.github.kitra.classes.ExchangeMessagesRequest
import com.ankumeah.github.kitra.objects.OkHttpClients
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

object BackendWebSocketManager {
  private const val BASE_URL: String = "wss://192.168.43.116"

  private val request = Request.Builder().url("$BASE_URL/ws/exchange_messages").build()

  private val gson = Gson()
  private val messagesFlow = MutableSharedFlow<ExchangeMessagesRequest>(extraBufferCapacity = 256, replay = 1)
  val messages = messagesFlow.asSharedFlow()

  private object Listener: WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
      Log.d("BackendWebSocketManager" ,"exchange message WebSocket connected")
    }
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) { Log.d("BackendWebSocketManager" ,"exchange message WebSocket closed") }
    override fun onMessage(webSocket: WebSocket, text: String) {
      try {
        Log.d("BackendWebsocketManager", "Received massage: $text")
        val msg = gson.fromJson(text, ExchangeMessagesRequest::class.java)
        messagesFlow.tryEmit(msg)
      }
      catch (e: JsonSyntaxException) {
        Log.e("BackendWebSocketManager", "Invalid json from backend: $e")
      }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
      Log.e("BackendWebSocketManager", "Error while opening websocket to backend: ${t.toString()}")
    }
  }

  val webSocket = OkHttpClients.SharedOkHttpClient.newWebSocket(request, Listener)
}