package com.codemine.Talk2MeServer

import net.sf.json.JSONObject
import java.io.*
import java.net.ServerSocket
import java.net.Socket

/**
 * Created by billy on 2016/11/19.
 */

/**
 * 路由器
 */
class Route(val serverSocket: ServerSocket) : Runnable {

    override fun run() {
        try {
            while (true) {
                dispatch(serverSocket.accept())
            }
        }
        catch (e: Exception) {
            Thread(Route(serverSocket)).start()
        }
    }

    /**
     * 分配socket
     */
    fun dispatch(socket: Socket) {
        val bfr = BufferedReader(InputStreamReader(socket.inputStream))
        val jsonObj = JSONObject.fromObject(bfr.readLine())
        println("op: " + jsonObj.getString("op"))
        when (jsonObj.getString("op")) {
            "login" -> {
                Thread(Login(socket, jsonObj)).start()
            }
            "register" -> {
                Thread(Register(socket, jsonObj)).start()
            }
            "getFriends" -> {
                Thread(GetFriends(socket, jsonObj)).start()
            }
            "getNewMsg" -> {
                Thread(GetNewMsg(socket, jsonObj)).start()
            }
            "shakeNewFriend" -> {
                Thread(ShakeNewFriend(socket, jsonObj)).start()
            }
            "sendNewMsg" -> {
                Thread(SendNewMsg(socket, jsonObj)).start()
            }
            else -> {
                println("error op")
            }
        }
    }

}