package com.example.mywaycompose.domain.model

import android.util.Log
import com.example.mywaycompose.utils.Constants.INCORRECT_TASK_ERROR
import com.example.mywaycompose.utils.Constants.INCORRECT_TIME_ERROR
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Task(
    var id:Int? = null,
    val task:String,
    var time:String,
    val date:String,
    var status:Boolean,
    val idBigTask:String? = null,
    val idSubTask:Int? = null,
    val grade:Int? = null,
    var mainTaskImage:String? = null,
    val subtaskColor:String? = null,
    val mainTaskTitle:String? = null,
    val subtaskTitle:String? = null,
    var online_sync:Boolean = false,
    val hide:Boolean = false
)

fun Task.checkCorrectTask():String{
    val time =  try{
        val parser = DateTimeFormatter.ofPattern(if(time.length == 5) "HH:mm" else "H:mm")
        val localTime = LocalTime.parse(time, parser)
        true
    }catch (e:Exception){
        false
    }
    val task = task.length > 3
    if(!time) return INCORRECT_TIME_ERROR
    if(!task) return INCORRECT_TASK_ERROR
    return ""
}

