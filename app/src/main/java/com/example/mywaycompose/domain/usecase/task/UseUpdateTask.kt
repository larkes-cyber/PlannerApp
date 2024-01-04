package com.example.mywaycompose.domain.usecase.task

import com.example.mywaycompose.domain.mapper.toTaskEntity
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.TaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class UseUpdateTask(
    val taskRepository: TaskRepository
) {

    operator fun invoke(task:Task): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            taskRepository.insertTask(task.toTaskEntity())
            emit(Resource.Success(true))
        }catch (e:Exception){
            emit(Resource.Error(""))
        }
    }

}