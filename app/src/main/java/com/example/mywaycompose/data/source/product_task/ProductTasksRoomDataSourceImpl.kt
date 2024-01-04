package com.example.mywaycompose.data.source.product_task

import com.example.mywaycompose.data.local.database.dao.ProductTasksDao
import com.example.mywaycompose.data.local.database.entity.ProductTaskEntity

class ProductTasksRoomDataSourceImpl(
    private val productTasksDao: ProductTasksDao
):ProductTasksRoomDataSource {
    override suspend fun insertProductTask(productTaskEntity: ProductTaskEntity) {
        productTasksDao.insertProductTask(productTaskEntity)
    }

    override suspend fun observeProductTasks(): List<ProductTaskEntity> {
        return productTasksDao.observeAllProductTasks()
    }

    override suspend fun deleteProductTask(productTaskEntity: ProductTaskEntity) {
        productTasksDao.deleteProductTask(productTaskEntity)
    }

    override suspend fun nukeProductTasksDatabase() {
        productTasksDao.nukeProductTasksDatabase()
    }
}