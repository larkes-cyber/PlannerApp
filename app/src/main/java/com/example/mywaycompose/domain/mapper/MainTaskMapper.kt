package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.local.database.entity.MainTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseMainTask
import com.example.mywaycompose.domain.model.MainTask

fun FirebaseMainTask.toMainTask(): MainTask {
    return MainTask(
        id = this.id!!,
        title = this.title!!,
        icon = this.icon!!,
        imageSrc = ""
    )
}

fun MainTask.toFirebaseMainTask(): FirebaseMainTask {
    return FirebaseMainTask(
        id = this.id,
        title = this.title,
        icon = this.icon
    )
}
fun MainTask.toMainTaskEntity(): MainTaskEntity {
    return MainTaskEntity(
        id = id,
        title = title,
        icon = icon,
        imageSrc = imageSrc,
        idIdea = idIdea,
        online_sync = online_sync,
        hide = hide
    )
}

fun MainTaskEntity.toMainTask():MainTask{
    return MainTask(
        id = id,
        title = title,
        icon = icon,
        imageSrc = imageSrc,
        idIdea = idIdea,
        online_sync = online_sync,
        hide = hide
    )
}