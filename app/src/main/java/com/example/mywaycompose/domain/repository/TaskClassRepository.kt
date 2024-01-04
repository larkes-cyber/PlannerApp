package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.data.local.database.entity.TaskClassEntity
import com.example.mywaycompose.domain.model.TaskClass
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TaskClassRepository {

    suspend fun insertTaskClass(taskClass: TaskClassEntity)
    suspend fun observeTaskClasses():List<TaskClassEntity>
    suspend fun syncTaskClasses(): Flow<Resource<String>>
    suspend fun deleteClass(taskClass: TaskClassEntity)
    suspend fun findClassByVsTaskId(id:String):TaskClassEntity?

}