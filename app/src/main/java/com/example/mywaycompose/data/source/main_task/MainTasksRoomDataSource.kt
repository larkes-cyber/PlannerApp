package com.example.mywaycompose.data.source.main_task

import com.example.mywaycompose.data.local.database.entity.MainTaskEntity

interface MainTasksRoomDataSource {

    suspend fun insertMainTask(mainTaskEntity: MainTaskEntity)
    suspend fun observeAllMainTasks():List<MainTaskEntity>
    suspend fun observeMainTaskById(id:String): MainTaskEntity
    suspend fun observeMainTaskByTitle(title:String): MainTaskEntity
    suspend fun deleteMainTask(task: MainTaskEntity)
    suspend fun updateMainTask(task:MainTaskEntity)
    suspend fun nukeMainTasksDatabase()

}