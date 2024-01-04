package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTaskEntity
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UseGetAllVisualTasks(
    private val visualTaskRepository: VisualTaskRepository
) {

    suspend fun execute():List<VisualTask>{
       val tasks = visualTaskRepository.getAllVisualTasks().toMutableList().map { it.toVisualTask() }.toMutableList()
        tasks.add(VisualTask(id = "-2", text = "", mainTaskId = "-1", dependIds = mutableListOf(), parentId = "-3"))
        tasks.forEach {
            if(it.dependIds.isEmpty() && it.text.isNotEmpty()){
                it.dependIds.add("-2")
            }
        }
        return tasks
    }

}