package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.local.database.entity.VisualTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask
import com.example.mywaycompose.domain.model.VisualTask

fun VisualTaskEntity.toVisualTask(): VisualTask {
    var arr = listOf<String>()
    if(dependIds.isNotEmpty()){
        arr = dependIds.split(" ")
    }
    return VisualTask(
        id = id ?: "21",
        text = text,
        dependIds = arr.toMutableList(),
        mainTaskId = mainTaskId,
        parentId = parentId,
        online_sync = online_sync,
        hide = hide
    )
}
fun FirebaseVisualTask.toVisualTask(): VisualTask{
    var arr = mutableListOf<String>()
    if(!dependIds.isNullOrEmpty()){
        arr = dependIds.split(" ").toMutableList()
    }
    return VisualTask(
        id = id,
        text = text!!,
        dependIds = arr,
        mainTaskId = mainTaskId!!,
        parentId = parentId!!,
        fromServer = true
    )
}

fun VisualTask.toVisualTaskEntity(): VisualTaskEntity {
    val arr = dependIds.map { it.toString() }.joinToString(" ")
    return VisualTaskEntity(
        id = id!!,
        text = text,
        dependIds = arr,
        mainTaskId = mainTaskId,
        parentId = parentId,
        online_sync = online_sync,
        hide = hide
    )
}

fun VisualTask.toFirebaseVisualTask(): FirebaseVisualTask {
    val arr = dependIds.map { it.toString() }.joinToString(" ")

    return FirebaseVisualTask(
        id = id,
        text = text,
        dependIds = arr,
        mainTaskId = mainTaskId,
        parentId = parentId
    )
}
