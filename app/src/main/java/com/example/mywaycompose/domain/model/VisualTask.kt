package com.example.mywaycompose.domain.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class VisualTask(
    val id:String? = null,
    var text:String,
    val dependIds:MutableList<String>,
    val mainTaskId:String,
    val parentId:String,
    val fromServer:Boolean = false,
    var xOffset: Dp = 0.dp,
    var yOffset:Dp = 0.dp,
    var online_sync:Boolean = false,
    var hide:Boolean = false
)