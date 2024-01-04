package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toFirebaseVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTaskEntity
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.TaskClassRepository
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UseDeleteVisualTask(
    private val taskClassRepository: TaskClassRepository,
    private val visualTaskRepository: VisualTaskRepository,
    private val useGetAllVisualTasks: UseGetAllVisualTasks
) {

    private suspend fun deleteClass(id:String){
        val classTask = taskClassRepository.findClassByVsTaskId(id)
        if(classTask != null){
            taskClassRepository.deleteClass(classTask)
        }
    }

    private suspend fun deleteDeepNodes(visualTask:VisualTask){

        if(visualTask.dependIds.isEmpty()) {
            visualTaskRepository.deleteVisualTask(visualTask.toVisualTaskEntity())
            deleteClass(visualTask.id!!)
            return
        }
        visualTaskRepository.getAllVisualTasks().forEach {
            if(it.id in visualTask.dependIds){
                visualTaskRepository.deleteVisualTask(it)
                deleteClass(it.id)
                deleteDeepNodes(it.toVisualTask())
            }
        }

    }

    suspend fun execute(visualTask: VisualTask){
        if(visualTask.parentId != "-1") {
            val parentTask = useGetAllVisualTasks.execute().find { visualTask.parentId == it.id }
            parentTask!!.dependIds.remove(visualTask.id)
            visualTaskRepository.insertVisualTask(parentTask.toVisualTaskEntity())
            visualTaskRepository.pushVisualTaskFirebase(parentTask.toFirebaseVisualTask())
        }

        deleteDeepNodes(visualTask)

        visualTaskRepository.deleteVisualTask(visualTask.toVisualTaskEntity())
    }


}