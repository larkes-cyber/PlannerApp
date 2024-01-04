package com.example.mywaycompose.domain.usecase.product_task

import com.example.mywaycompose.domain.mapper.toFirebaseProductTask
import com.example.mywaycompose.domain.model.ProductTask
import com.example.mywaycompose.domain.repository.ProductTaskRepository

class UsePushProductTaskFirebase(
    private val productTaskRepository: ProductTaskRepository
) {

    fun execute(productTask: ProductTask){
        productTaskRepository.pushProductTaskFirebase(productTask.toFirebaseProductTask())
    }

}