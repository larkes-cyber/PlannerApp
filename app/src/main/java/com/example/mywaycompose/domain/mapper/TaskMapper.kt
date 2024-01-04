package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.local.database.entity.TaskEntity
import com.example.mywaycompose.domain.model.Task

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id  = id,
        task = task,
        time = time,
        date = date,
        status = status,
        idBigTask = idBigTask,
        idSubTask = idSubTask,
        grade = grade,
        mainTaskImage = mainTaskImage,
        subtaskTitle = subtaskTitle,
        subtaskColor = subtaskColor,
        mainTaskTitle = mainTaskTitle,
        online_sync = online_sync,
        hide = hide
    )
}

fun TaskEntity.toTask(): Task {

    return Task(
        id  = id,
        task = task,
        time = time,
        date = date,
        status = status,
        idBigTask = idBigTask,
        idSubTask = idSubTask,
        grade = grade,
        mainTaskImage = mainTaskImage,
        subtaskTitle = subtaskTitle,
        subtaskColor = subtaskColor,
        mainTaskTitle = mainTaskTitle,
        online_sync = online_sync,
        hide = hide
    )
}