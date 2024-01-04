package com.example.mywaycompose.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainTaskEntity(
    @PrimaryKey(autoGenerate = false)
    var id:String,
    val title:String,
    val icon:String?,
    val imageSrc:String? = null,
    var idIdea:Int? = null,
    var online_sync:Boolean,
    var hide:Boolean
)