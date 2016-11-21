package com.codemine.Talk2MeServer

import com.sun.tools.internal.ws.wsdl.document.Input
import net.sf.json.JSONObject
import java.io.*
import java.net.Socket
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.zip.CheckedOutputStream

/**
 * Created by billy on 2016/11/19.
 */

/**
 * 登录
 */
class Login(val socket: Socket, val jsonObject: JSONObject) : Runnable {

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

    fun query() : Boolean {
//        println("in query")
        val account = jsonObject.getString("account")
        val password = jsonObject.getString("password")
        sql = "SELECT * FROM USERS_INFO WHERE account='$account';"
        val result = statement!!.executeQuery(sql)
        if(result.next()) {
            if(result.getString("password") == password) {
                bfw.write("login success\n")
                bfw.flush()
                return true
            }
            else {
                bfw.write("error password\n")
                bfw.flush()
            }
        }
        bfw.write("account not exist\n")
        bfw.flush()
        return false
    }

    override fun run() {
        query()
    }
}