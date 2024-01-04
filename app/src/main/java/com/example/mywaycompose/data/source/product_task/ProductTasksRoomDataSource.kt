package com.example.mywaycompose.data.source.product_task

import com.example.mywaycompose.data.local.database.entity.ProductTaskEntity

interface ProductTasksRoomDataSource {

    suspend fun insertProductTask(productTaskEntity: ProductTaskEntity)
    suspend fun observeProductTasks():List<ProductTaskEntity>
    suspend fun deleteProductTask(productTaskEntity: ProductTaskEntity)
    suspend fun nukeProductTasksDatabase()

}