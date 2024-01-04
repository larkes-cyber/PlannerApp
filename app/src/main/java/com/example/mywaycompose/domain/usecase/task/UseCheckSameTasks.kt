package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.repository.TaskRepository

class UseCheckSameTasks(
   private val taskRepository: TaskRepository
    ) {

    suspend fun execute(time:String, date:String):Boolean{
        return !taskRepository.checkSameTask(time,date)
    }

}