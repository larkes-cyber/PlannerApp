package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UseGetAllVisualTasksFromDatabase(
    private val visualTaskRepository: VisualTaskRepository
) {

    suspend fun execute(id:String): List<VisualTask> {

        return visualTaskRepository.getAllVisualTasksByMainTaskId(id).map { it.toVisualTask() }

    }

}