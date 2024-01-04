package com.example.mywaycompose.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TaskClassEntity(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val title:String,
    val color:Long,
    var online_sync:Boolean,
    var visible:Boolean,
    val vsTaskId:String
)