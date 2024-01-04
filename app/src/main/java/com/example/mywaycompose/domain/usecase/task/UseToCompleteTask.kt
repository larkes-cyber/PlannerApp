package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.mapper.toFirebaseTask
import com.example.mywaycompose.domain.mapper.toTaskEntity
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.TaskRepository

class UseToCompleteTask(
    private val taskRepository: TaskRepository
) {

    suspend fun execute(task:Task){
        task.status = true
        taskRepository.pushTaskFirebase(task.toFirebaseTask())
        taskRepository.insertTask(task.toTaskEntity())
    }

}
