package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.mapper.toFirebaseMainTask
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository

class UsePushMainTaskFirebase(
    private val mainTaskRepository: MainTaskRepository
) {

    fun execute(mainTask: MainTask){
        mainTaskRepository.pushMainTaskFirebase(mainTask.toFirebaseMainTask())
    }

}