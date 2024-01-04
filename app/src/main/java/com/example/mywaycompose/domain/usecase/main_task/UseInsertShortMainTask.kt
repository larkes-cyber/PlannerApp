package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.mapper.toFirebaseMainTask
import com.example.mywaycompose.domain.mapper.toMainTaskEntity
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository

class UseInsertShortMainTask(
    private val mainTaskRepository: MainTaskRepository
) {

    suspend fun execute(mainTask: MainTask, serverSync:Boolean = false){
        mainTaskRepository.addMainTask(mainTask.toMainTaskEntity())
        if(serverSync) mainTaskRepository.pushMainTaskFirebase(mainTask.toFirebaseMainTask())
    }

}