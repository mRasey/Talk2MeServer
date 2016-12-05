package com.codemine.Talk2MeServer

import java.net.ServerSocket

/**
 * Created by billy on 2016/11/19.
 */

/**
 * 主线程
 */
fun main(args: Array<String>) {
    val serverSocket = ServerSocket(2333)
    Thread(Route(serverSocket)).start()
}