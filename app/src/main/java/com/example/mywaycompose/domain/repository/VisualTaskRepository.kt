package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.data.local.database.entity.VisualTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface VisualTaskRepository {

    suspend fun insertVisualTask(visualTask: VisualTaskEntity)
    suspend fun getAllVisualTasksByMainTaskId(id:String):List<VisualTaskEntity>
    suspend fun updateVisualTask(visualTask: VisualTaskEntity)
    suspend fun getVisualTaskById(id:String):VisualTaskEntity
    suspend fun getAllVisualTasks():List<VisualTaskEntity>
    suspend fun findVisualMainTask(id:String):VisualTaskEntity
    suspend fun deleteVisualTask(visualTask: VisualTaskEntity)
    fun pushVisualTaskFirebase(visualTask: FirebaseVisualTask)
    fun deleteVisualTaskFirebase(visualTask: FirebaseVisualTask)
    fun syncVisualTasks():Flow<Resource<String>>
    suspend fun findVisualTaskByParentId(id:String):VisualTaskEntity

}