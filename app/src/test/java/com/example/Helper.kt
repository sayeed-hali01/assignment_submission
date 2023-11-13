package com.example

import java.io.InputStreamReader

object Helper {
    fun readFileResource(fileName:String):String{

        val inputStream = Helper::class.java.getResourceAsStream(fileName)

        val builder = java.lang.StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")

        reader.readLines().forEach{
            builder.append(it)
        }
        return builder.toString()
    }
    fun readFakeFile():String{
        val inputStream = Helper::class.java.getResourceAsStream("/response.json")

        val builder = java.lang.StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")

        reader.readLines().forEach{
            builder.append(it)
        }
        return builder.toString()
    }
}