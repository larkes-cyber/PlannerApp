package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.TaskRepository

class UseNukeDatabase(
    private val taskRepository: TaskRepository
) {

    suspend fun execute(){
        taskRepository.cleanTasksDatabase()
    }

}