package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.data.local.database.entity.MainTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseMainTask
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainTaskRepository {
    suspend fun addMainTask(mainTask:MainTaskEntity)
    suspend fun getAllMainTasks():List<MainTaskEntity>
    suspend fun getMainTaskById(id:String):MainTaskEntity
    suspend fun getMainTaskBytTitle(title:String):MainTaskEntity
    suspend fun deleteMainTask(task:MainTaskEntity)
    suspend fun updateMainTask(task:MainTaskEntity)
    fun pushMainTaskFirebase(mainTask: FirebaseMainTask)
    fun pushMainTaskImageFirebase(image:ByteArray, id:String)
    fun deleteMainTaskFirebase(mainTask: FirebaseMainTask)
    fun syncMainTasks():Flow<Resource<String>>
}