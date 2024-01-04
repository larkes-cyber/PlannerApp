package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.local.database.entity.TaskClassEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseTaskClass
import com.example.mywaycompose.domain.model.TaskClass

fun TaskClass.toTaskClassEntity():TaskClassEntity{
    return TaskClassEntity(
        id = id,
        title = title,
        color = color,
        online_sync = online_sync,
        visible = visible,
        vsTaskId = vsTaskId
    )
}

fun TaskClassEntity.toTaskClass():TaskClass{
    return TaskClass(
        id = id,
        title = title,
        color = color,
        online_sync = online_sync,
        visible = visible,
        vsTaskId = vsTaskId
    )
}

fun TaskClass.toFirebaseTaskClass():FirebaseTaskClass{
    return FirebaseTaskClass(
        id = id,
        title = title,
        color = color,
        vsTaskId = vsTaskId
    )
}

fun FirebaseTaskClass.toTaskClass():TaskClass{
    return TaskClass(
        id = id!!,
        title = title!!,
        color = color!!,
        online_sync = true,
        visible = true,
        vsTaskId = vsTaskId!!
    )
}