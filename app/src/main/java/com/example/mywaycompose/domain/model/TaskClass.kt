package com.example.mywaycompose.domain.model

import java.util.*

data class TaskClass(
    val id:String = UUID.randomUUID().toString(),
    val title:String,
    val color:Long,
    var online_sync:Boolean,
    var visible:Boolean = true,
    val vsTaskId:String
)