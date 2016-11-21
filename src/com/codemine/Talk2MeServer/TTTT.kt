package com.codemine.Talk2MeServer

import net.sf.json.JSONObject
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.Socket
import java.util.*

/**
 * Created by billy on 2016/11/19.
 */
fun main(args: Array<String>) {
    val jsonObj = JSONObject()
    for (i in 0..9) {
        jsonObj.put(i.toString(), i.toString())
    }
    for (i in 0..10) {
        if (jsonObj.containsKey(i.toString()))
            println(jsonObj.getString(i.toString()))
    }
}