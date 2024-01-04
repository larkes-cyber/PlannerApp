package com.example.mywaycompose.domain.usecase.product_task

import com.example.mywaycompose.domain.mapper.toProductTaskEntity
import com.example.mywaycompose.domain.model.ProductTask
import com.example.mywaycompose.domain.repository.ProductTaskRepository

class UseInsertProductTask(
    private val productTasksRepository: ProductTaskRepository
) {

    suspend fun execute(productTask: ProductTask){
        productTasksRepository.insertProductTask(productTask.toProductTaskEntity())
    }

}