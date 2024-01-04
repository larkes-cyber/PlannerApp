package com.example.mywaycompose.domain.usecase.task_class

import com.example.mywaycompose.domain.mapper.toTaskClass
import com.example.mywaycompose.domain.model.TaskClass
import com.example.mywaycompose.domain.repository.TaskClassRepository

class UseObserveTaskClasses(
   private val taskClassRepository: TaskClassRepository
) {

    suspend fun execute():List<TaskClass>{
        return taskClassRepository.observeTaskClasses().map { it.toTaskClass() }
    }

}