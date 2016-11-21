package com.codemine.Talk2MeServer

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
 * 注册
 */
class Register (val socket: Socket, val jsonObject: JSONObject): Runnable {

    var statement: Statement? = null
    var sql: String? = null
    var bfr = BufferedReader(InputStreamReader(socket.inputStream))
    var bfw = BufferedWriter(OutputStreamWriter(socket.outputStream))

    init {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://localhost:3306/Talk2Me?user=root&password=199507wz"
            val connection = DriverManager.getConnection(url)
            if (!connection.isClosed())
                println("数据库连接成功!")
            statement = connection.createStatement()
        }
        catch (e: SQLException) {
            println("error")
        }
        catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    fun exit() : Boolean {
        val account = jsonObject.getString("account")
//        println("account   $account")
        sql = "SELECT * FROM USERS_INFO WHERE account='$account';"
        val result = statement!!.executeQuery(sql)
        if(result.next()) {
            return true
        }
        return false
    }

    fun insert(): Int {
        val account = jsonObject.get("account")
        val password = jsonObject.get("password")
        val email = jsonObject.get("email")
        sql = "INSERT " +
                "INTO USERS_INFO " +
                "VALUES ('$account', '$password', '$email');"
        return statement!!.executeUpdate(sql)
    }

    override fun run() {
        if(exit()) {
            bfw.write("account already exist\n")
        }
        else if(insert() == 1) {
            bfw.write("register success\n");
        }
        else {
            bfw.write("register failed\n")
        }
        bfw.flush()
    }
}