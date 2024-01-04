package com.example.mywaycompose.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductTaskEntity(
    @PrimaryKey(autoGenerate = false)
    var id:String,
    val task:String,
    val goalId:String,
    var online_sync:Boolean,
    var hide:Boolean
)