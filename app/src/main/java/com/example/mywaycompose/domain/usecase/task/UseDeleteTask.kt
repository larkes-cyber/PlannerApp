package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.mapper.toFirebaseTask
import com.example.mywaycompose.domain.mapper.toTaskEntity
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.TaskRepository

class UseDeleteTask(
    private val taskRepository: TaskRepository
) {

    suspend fun execute(task:Task){
        taskRepository.deleteTask(task.toTaskEntity())
    }

}