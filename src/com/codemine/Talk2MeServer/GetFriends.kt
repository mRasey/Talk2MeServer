package com.codemine.Talk2MeServer

import net.sf.json.JSONObject
import java.io.*
import java.net.Socket
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class GetFriends(val socket: Socket, val jsonObject: JSONObject) : Runnable {

    var statement : Statement? = null
    var sql: String? = null
//        var jsonObject: JSONObject? = null
    var bfr = BufferedReader(InputStreamReader(socket.inputStream))
    var bfw = BufferedWriter(OutputStreamWriter(socket.outputStream))

    init {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://localhost:3306/Talk2Me?user=root&password=199507wz"
            val connection = DriverManager.getConnection(url)
            if (!connection.isClosed)
                println("数据库连接成功!")
            statement = connection.createStatement()
//            jsonObject = JSONObject.fromObject(bfr.readLine())
//            println("finish")

        } catch (e: SQLException) {
            println("error")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getFriends() {
        val callbackJson = JSONObject()
        val map = HashMap<String, String>()
        val account = jsonObject.getString("account");
        sql = "SELECT account2 FROM FRIENDSHIP WHERE account1='$account'"
        val result = statement!!.executeQuery(sql)
        var i = 0
        while (result.next()) {
            map.put(i.toString(), result.getString("account2"))
            i++
            println(result.getString("account2"))
        }
        callbackJson.putAll(map)
        bfw.write(callbackJson.toString() + "\n")
        bfw.flush()
    }

    override fun run() {
        getFriends()
    }
}
