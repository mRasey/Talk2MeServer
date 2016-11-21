package com.codemine.Talk2MeServer

import net.sf.json.JSONObject
import java.io.*
import java.net.Socket

/**
 * Created by billy on 2016/11/19.
 */

/**
 * 路由器
 */
class Route() {

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
            else -> {
                println("error op")
            }
        }
    }

}