package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toFirebaseVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTaskEntity
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UseAddNewDependNode(
    private val visualTaskRepository: VisualTaskRepository
) {

    suspend fun execute(visualTask: VisualTask){
        if(visualTask.parentId != "-1") {
            val parentNode = visualTaskRepository.getAllVisualTasks().map { it.toVisualTask() }
                .find { it.id == visualTask.parentId }!!
            parentNode.dependIds.add(visualTask.id!!)
            visualTaskRepository.insertVisualTask(parentNode.toVisualTaskEntity())
        }

        visualTaskRepository.insertVisualTask(visualTask.toVisualTaskEntity())
    }

}