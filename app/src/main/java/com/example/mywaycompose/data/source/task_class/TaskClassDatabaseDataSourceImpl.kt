package com.example.mywaycompose.data.source.task_class

import com.example.mywaycompose.data.local.database.dao.TaskClassDao
import com.example.mywaycompose.data.local.database.entity.TaskClassEntity

class TaskClassDatabaseDataSourceImpl(
    private val taskClassDao: TaskClassDao
):TaskClassDatabaseDataSource {
    override suspend fun insertTaskClass(taskClass: TaskClassEntity) {
        taskClassDao.insertTaskClass(taskClass)
    }

    override suspend fun observeTaskClasses(): List<TaskClassEntity> {
        return taskClassDao.observeTaskClasses()
    }

    override suspend fun nukeDatabase() {
        taskClassDao.nukeDatabase()
    }

    override suspend fun deleteTaskClass(taskClass: TaskClassEntity) {
        taskClassDao.deleteTaskClass(taskClass)
    }

    override suspend fun findTaskClassByVsTaskId(id: String):TaskClassEntity? {
        return taskClassDao.findClass(id)
    }
}