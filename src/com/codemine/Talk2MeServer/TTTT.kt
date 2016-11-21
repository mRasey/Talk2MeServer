package com.codemine.Talk2MeServer

import net.sf.json.JSONArray
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
    val array = ArrayList<obj>()
    val hashmap = HashMap<String, ArrayList<obj>>()
    array.add(obj("1", "1"))
    array.add(obj("2", "2"))
    hashmap.put("233", array)
    val jsonObj = JSONObject.fromObject(hashmap)
    val newj = JSONObject.fromObject(jsonObj)
    val i = newj.get("233")
    println(JSONObject.fromObject(JSONArray.fromObject(i)[0]).getString("date"))
}

data class obj(val info: String, val date:String) {

}