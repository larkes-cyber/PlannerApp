package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.data.local.database.entity.ProductTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseProductTask
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductTaskRepository {

    suspend fun insertProductTask(productTaskEntity: ProductTaskEntity)
    suspend fun observeProductTasks():List<ProductTaskEntity>
    suspend fun deleteProductTask(productTaskEntity: ProductTaskEntity)

    fun pushProductTaskFirebase(productTask: FirebaseProductTask)
    fun deleteProductTaskFirebase(productTask: FirebaseProductTask)
    fun syncProductTasks(): Flow<Resource<String>>


}