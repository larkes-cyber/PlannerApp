package com.example.mywaycompose.data.source.task_class

import com.example.mywaycompose.data.local.database.entity.TaskClassEntity
import com.example.mywaycompose.domain.model.TaskClass

interface TaskClassDatabaseDataSource {

    suspend fun insertTaskClass(taskClass: TaskClassEntity)
    suspend fun observeTaskClasses():List<TaskClassEntity>
    suspend fun nukeDatabase()
    suspend fun deleteTaskClass(taskClass: TaskClassEntity)
    suspend fun findTaskClassByVsTaskId(id:String):TaskClassEntity?

}