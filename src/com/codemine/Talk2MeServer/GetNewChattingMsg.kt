package com.codemine.Talk2MeServer

import net.sf.json.JSONObject
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.Socket
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

/**
 * Created by billy on 2016/11/21.
 */

class GetNewChattingMsg(val socket: Socket, val jsonObject: JSONObject) : Runnable {
    var statement : Statement? = null
    var sql: String? = null
    var bfw = BufferedWriter(OutputStreamWriter(socket.outputStream))

    init {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://localhost:3306/Talk2Me?user=root&password=199507wz"
            val connection = DriverManager.getConnection(url)
            if (!connection.isClosed)
                println("数据库连接成功!")
            statement = connection.createStatement()

        } catch (e: SQLException) {
            println("error")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getNewChattingMsg() {
        sql = "SELECT info FROM "
    }

    override fun run() {
        getNewChattingMsg()
    }
}
