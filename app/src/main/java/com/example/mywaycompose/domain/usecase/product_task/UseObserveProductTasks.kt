package com.example.mywaycompose.domain.usecase.product_task

import com.example.mywaycompose.domain.mapper.toProductTask
import com.example.mywaycompose.domain.model.ProductTask
import com.example.mywaycompose.domain.repository.ProductTaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UseObserveProductTasks(
    private val productTaskRepository: ProductTaskRepository
) {

    operator fun invoke(): Flow<Resource<List<ProductTask>>> = flow {

        emit(Resource.Loading())

        try {
            val tasks = productTaskRepository.observeProductTasks().map { it.toProductTask() }
            emit(Resource.Success(tasks))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }

    }

}