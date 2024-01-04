package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UseFindVisualMainTask(
    private val visualTaskRepository: VisualTaskRepository
) {

    suspend fun execute(id:String):VisualTask{
        return visualTaskRepository.findVisualMainTask(id).toVisualTask()
    }

}