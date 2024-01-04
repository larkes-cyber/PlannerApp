package com.example.mywaycompose.data.source.task

import android.content.Context
import com.example.mywaycompose.data.local.database.dao.TasksDao
import com.example.mywaycompose.data.local.database.entity.TaskEntity

class TasksRoomDataSourceImpl(
    private val tasksDao: TasksDao,
    private val context: Context
):TasksRoomDataSource {
    override suspend fun insertTask(task: TaskEntity) {
        tasksDao.addTask(task)
    }

    override suspend fun observeTasksByDate(date: String): List<TaskEntity> = tasksDao.getTasksByDate(date)

    override suspend fun observeTaskById(id: Int): TaskEntity = tasksDao.getTaskById(id)

    override suspend fun updateTask(task: TaskEntity) {
        tasksDao.addTask(task)
    }

    override suspend fun checkSameTask(time: String, date: String): Boolean = tasksDao.isThereTheSame(time, date) == null

    override suspend fun deleteTask(task: TaskEntity) = tasksDao.removeTask(task)

    override suspend fun observeAllTasks(): List<TaskEntity> = tasksDao.getAllTasks()

    override suspend fun cleanTasksDatabase() {
        tasksDao.nukeTaskDatabase()
    }
}