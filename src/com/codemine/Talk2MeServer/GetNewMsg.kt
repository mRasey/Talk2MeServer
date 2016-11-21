package com.codemine.Talk2MeServer

import com.sun.tools.classfile.InnerClasses_attribute
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

class GetNewMsg(val socket: Socket, val jsonObject: JSONObject) : Runnable{

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

    fun getNewMsg() {
        val account = jsonObject.getString("account")
        sql = "SELECT * FROM CHAT_RECORD WHERE toAccount='$account'"
        val result = statement!!.executeQuery(sql)
        val infoArray = ArrayList<InfoObj>()
        while (result.next()) {
            val fromAccount = result.getString("fromAccount")
            val toAccount = result.getString("toAccount")
            val date = result.getString("date")
            val info = result.getString("info")
            infoArray.add(InfoObj(fromAccount, toAccount, info, date))
        }
        val hashMap = HashMap<String, ArrayList<InfoObj>>()
        hashMap.put("info", infoArray)
        bfw.write(JSONObject.fromObject(hashMap).toString() + "\n")
        bfw.flush()

        sql = "DELETE FROM CHAT_RECORD WHERE toAccount = '$account'"
        statement!!.executeUpdate(sql) //删除已取出的消息
    }

    override fun run() {
        getNewMsg()
    }
}

data class InfoObj(val fromAccount: String, val toAccount: String, val info: String, val date: String) {

}