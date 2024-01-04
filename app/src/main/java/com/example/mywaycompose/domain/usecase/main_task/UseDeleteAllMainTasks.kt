package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository

class UseDeleteAllMainTasks(
    private val mainTaskRepository: MainTaskRepository,
    private val serviceRepository: ServiceRepository
) {

    suspend fun execute(){
        val tasks = mainTaskRepository.getAllMainTasks()
        tasks.forEach {
            val file = serviceRepository.getImageFileById(it.id!!)
            serviceRepository.deleteFile(file)
        }
    }

}