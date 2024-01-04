package com.example.mywaycompose.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VisualTaskEntity(
    @PrimaryKey(autoGenerate = false)
    var id:String,
    var text:String,
    val dependIds:String,
    val mainTaskId:String,
    val parentId:String,
    var online_sync:Boolean,
    var hide:Boolean
)