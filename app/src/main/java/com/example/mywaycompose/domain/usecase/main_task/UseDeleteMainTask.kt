package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.mapper.toFirebaseMainTask
import com.example.mywaycompose.domain.mapper.toFirebaseVisualTask
import com.example.mywaycompose.domain.mapper.toMainTaskEntity
import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UseDeleteMainTask(
    private val mainTaskRepository: MainTaskRepository,
    private val visualTasksLocalRepository: VisualTaskRepository,
) {

    suspend fun execute(mainTask: MainTask){

        mainTaskRepository.deleteMainTask(mainTask.toMainTaskEntity())

    }

}