package com.example.mywaycompose.domain.model

data class ProductTask(
    val id:String,
    val task:String,
    val goalId:String,
    var online_sync:Boolean = false,
    val hide:Boolean = false
)