package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.data.local.database.entity.TaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insertTask(task: TaskEntity)
    suspend fun observeTasksByDate(date:String):List<TaskEntity>
    suspend fun observeTaskById(id:Int):TaskEntity
    suspend fun checkSameTask(time:String, date:String):Boolean
    suspend fun deleteTask(task:TaskEntity)
    suspend fun observeAllTasks():List<TaskEntity>
    suspend fun cleanTasksDatabase()
    fun syncTasks(): Flow<Resource<String>>
    fun pushTaskFirebase(task:FirebaseTask)
    fun deleteTaskFirebase(task:FirebaseTask)
}