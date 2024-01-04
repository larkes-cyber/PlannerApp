package com.example.mywaycompose.domain.usecase.product_task

import com.example.mywaycompose.domain.mapper.toFirebaseProductTask
import com.example.mywaycompose.domain.mapper.toProductTaskEntity
import com.example.mywaycompose.domain.model.ProductTask
import com.example.mywaycompose.domain.repository.ProductTaskRepository

class UseDeleteProductTask(
    private val productTaskRepository: ProductTaskRepository
) {

    suspend fun execute(productTask: ProductTask){
        productTaskRepository.deleteProductTask(productTask.toProductTaskEntity())
        productTaskRepository.deleteProductTaskFirebase(productTask.toFirebaseProductTask())
    }

}