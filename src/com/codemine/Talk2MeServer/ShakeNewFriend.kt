package com.codemine.Talk2MeServer

import net.sf.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.*

/**
 * Created by billy on 2016/11/21.
 */

class ShakeNewFriend(val socket: Socket, val jsonObject: JSONObject) : Runnable{

    var statement : Statement? = null
    var sql: String? = null
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
        } catch (e: SQLException) {
            println("error")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun shakeNewFriend() {
        val account = jsonObject.getString("account")
        sql = "SELECT account FROM USERS_INFO " +
                "WHERE (account != '$account' AND account NOT IN (SELECT account2 FROM FRIENDSHIP WHERE account1 = '$account'))"
        val result = statement!!.executeQuery(sql)
        val array = ArrayList<String>()
        while (result.next()) {
            array.add(result.getString("account"))
        }
        val newFriend = array[Random().nextInt(array.size)]
        val callbackJson = JSONObject()
        callbackJson.put("newFriend", newFriend)
        bfw.write(callbackJson.toString() + "\n")
        bfw.flush()
        //保存朋友关系
        sql = "INSERT INTO FRIENDSHIP VALUES ('$account', '$newFriend');"
        statement!!.executeUpdate(sql)
        sql = "INSERT INTO FRIENDSHIP VALUES ('$newFriend', '$account');"
        statement!!.executeUpdate(sql)
    }

    override fun run() {
        shakeNewFriend()
    }

}
