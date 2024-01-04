package com.example.mywaycompose.domain.model

import java.io.Serializable
import java.util.UUID

data class MainTask(
    var id:String,
    val title:String,
    val icon:String? = null,
    var imageSrc:String? = null,
    var idIdea:Int? = null,
    var online_sync:Boolean = false,
    var hide:Boolean = false
): Serializable

