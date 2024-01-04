package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask
import com.example.mywaycompose.domain.model.Task

fun FirebaseTask.toTask(): Task {
    return Task(
        id  = null,
        task = task!!,
        time = time!!,
        date = date!!,
        status = status!!,
        idBigTask = idBigTask,
        idSubTask = idSubTask,
        grade = grade,
        mainTaskImage = mainTaskImage,
        subtaskTitle = subtaskTitle,
        subtaskColor = subtaskColor,
        mainTaskTitle = mainTaskTitle
    )
}

fun Task.toFirebaseTask(): FirebaseTask {
    return FirebaseTask(
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
        mainTaskTitle = mainTaskTitle
    )
}