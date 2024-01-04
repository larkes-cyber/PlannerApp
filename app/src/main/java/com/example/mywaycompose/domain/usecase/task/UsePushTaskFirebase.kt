package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.mapper.toFirebaseTask
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.TaskRepository

class UsePushTaskFirebase(
    private val taskRepository: TaskRepository
) {

    fun execute(task:Task){
        taskRepository.pushTaskFirebase(task.toFirebaseTask())
    }

}