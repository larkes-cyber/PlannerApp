package com.example.mywaycompose.data.source.main_task

import android.util.Log
import com.example.mywaycompose.data.local.database.dao.MainTasksDao
import com.example.mywaycompose.data.local.database.entity.MainTaskEntity

class MainTasksRoomDataSourceImpl(
    private val mainTasksDao: MainTasksDao
):MainTasksRoomDataSource {
    override suspend fun insertMainTask(mainTaskEntity: MainTaskEntity) {
        mainTasksDao.addMainTask(mainTaskEntity)
    }

    override suspend fun observeAllMainTasks(): List<MainTaskEntity> = mainTasksDao.getAllMainTasks()

    override suspend fun observeMainTaskById(id: String): MainTaskEntity = mainTasksDao.getMainTaskById(id)

    override suspend fun observeMainTaskByTitle(title: String): MainTaskEntity = mainTasksDao.getMainTaskByTitle(title)

    override suspend fun deleteMainTask(task: MainTaskEntity) = mainTasksDao.deleteMainTask(task)
    override suspend fun updateMainTask(task: MainTaskEntity) {
        mainTasksDao.updateMainTask(task)
    }

    override suspend fun nukeMainTasksDatabase() {
        mainTasksDao.nukeMainTaskDatabase()
    }
}