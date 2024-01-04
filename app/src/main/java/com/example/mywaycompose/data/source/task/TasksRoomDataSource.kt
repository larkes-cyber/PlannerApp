package com.example.mywaycompose.data.source.task

import com.example.mywaycompose.data.local.database.entity.TaskEntity

interface TasksRoomDataSource {

    suspend fun insertTask(task: TaskEntity)
    suspend fun observeTasksByDate(date:String):List<TaskEntity>
    suspend fun observeTaskById(id:Int): TaskEntity
    suspend fun updateTask(task: TaskEntity)
    suspend fun checkSameTask(time:String, date:String):Boolean
    suspend fun deleteTask(task: TaskEntity)
    suspend fun observeAllTasks():List<TaskEntity>
    suspend fun cleanTasksDatabase()

}