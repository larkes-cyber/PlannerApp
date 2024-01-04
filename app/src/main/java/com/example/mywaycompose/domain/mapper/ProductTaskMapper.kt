package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.local.database.entity.ProductTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseProductTask
import com.example.mywaycompose.domain.model.ProductTask

fun ProductTask.toProductTaskEntity(): ProductTaskEntity {
    return ProductTaskEntity(
        id = id,
        task = task,
        goalId = goalId,
        online_sync = online_sync,
        hide = hide
    )
}

fun ProductTaskEntity.toProductTask(): ProductTask {
    return ProductTask(
        id = id,
        task = task,
        goalId = goalId,
        online_sync = online_sync,
        hide = hide
    )
}
fun ProductTask.toFirebaseProductTask(): FirebaseProductTask {
    return FirebaseProductTask(
        id = id,
        task = task,
        goalId = goalId
    )
}

fun FirebaseProductTask.toProductTask():ProductTask{
    return ProductTask(
        id = id!!,
        task = task!!,
        goalId = goalId
    )
}