package com.example.mywaycompose.data.source.visual_task

import com.example.mywaycompose.data.local.database.entity.VisualTaskEntity

interface VisualTaskRoomDataSource {

    suspend fun insertVisualTask(visualTaskEntity: VisualTaskEntity)
    suspend fun observeVisualTasksByMainTaskId(id:String):List<VisualTaskEntity>
    suspend fun observeVisualTaskById(id:String): VisualTaskEntity
    suspend fun observeAllVisualTasks():List<VisualTaskEntity>
    suspend fun deleteVisualTask(visualTaskEntity: VisualTaskEntity)
    suspend fun nukeVisualTaskDatabase()
    suspend fun findVisualTaskByParentId(id:String):VisualTaskEntity

}