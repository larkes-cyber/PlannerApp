package com.example.mywaycompose.data.source.visual_task

import com.example.mywaycompose.data.local.database.dao.VisualTaskDao
import com.example.mywaycompose.data.local.database.entity.VisualTaskEntity

class VisualTaskRoomDataSourceImpl(
    private val visualTaskDao: VisualTaskDao
):VisualTaskRoomDataSource {
    override suspend fun insertVisualTask(visualTaskEntity: VisualTaskEntity) = visualTaskDao.insertVisualTask(visualTaskEntity)

    override suspend fun observeVisualTasksByMainTaskId(id: String): List<VisualTaskEntity> = visualTaskDao.getAllVisualTasksByMainTaskId(id)

    override suspend fun observeVisualTaskById(id: String): VisualTaskEntity = visualTaskDao.getVisualTaskById(id)

    override suspend fun observeAllVisualTasks(): List<VisualTaskEntity> = visualTaskDao.getAllVisualTasks()

    override suspend fun deleteVisualTask(visualTaskEntity: VisualTaskEntity) = visualTaskDao.deleteVisualTask(visualTaskEntity)
    override suspend fun nukeVisualTaskDatabase() {
        visualTaskDao.nukeVisualTasksDatabase()
    }

    override suspend fun findVisualTaskByParentId(id: String): VisualTaskEntity {
        return visualTaskDao.findVsTaskByParentId(id)
    }
}