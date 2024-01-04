package com.example.mywaycompose.domain.usecase.task_class

import com.example.mywaycompose.domain.mapper.toTaskClassEntity
import com.example.mywaycompose.domain.model.TaskClass
import com.example.mywaycompose.domain.repository.TaskClassRepository

class UseInsertTaskClass(
    private val taskClassRepository: TaskClassRepository
) {

    suspend fun execute(taskClass:TaskClass){
        taskClassRepository.insertTaskClass(taskClass.toTaskClassEntity())
    }

}