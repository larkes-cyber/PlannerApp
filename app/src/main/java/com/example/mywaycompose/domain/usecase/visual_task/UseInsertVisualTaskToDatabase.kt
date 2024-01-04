package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTaskEntity
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository


class UseInsertVisualTaskToDatabase(
    private val visualTaskRepository: VisualTaskRepository,
    private val usePushVisualTaskToFirebase: UsePushVisualTaskToFirebase
    ) {

    suspend fun execute(visualTask: VisualTask){
        if(visualTask.parentId != "-1"&& !visualTask.fromServer){
            val parent = visualTaskRepository.getVisualTaskById(visualTask.parentId).toVisualTask()
            parent.dependIds.add(visualTask.id!!)
            visualTaskRepository.updateVisualTask(parent.toVisualTaskEntity())
            usePushVisualTaskToFirebase.execute(parent)
        }
        visualTaskRepository.insertVisualTask(visualTask.toVisualTaskEntity())
    }

}