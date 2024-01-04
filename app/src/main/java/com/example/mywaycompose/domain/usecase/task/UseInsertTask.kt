package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.mapper.toTaskEntity
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.TaskRepository
import javax.inject.Inject

class UseInsertTask @Inject constructor(private val taskRepository: TaskRepository) {
    suspend fun execute(task:Task){
        taskRepository.insertTask(task.toTaskEntity())
    }
}