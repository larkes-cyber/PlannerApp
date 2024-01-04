package com.example.mywaycompose.domain.usecase.task

import android.util.Log
import com.example.mywaycompose.domain.mapper.toTask
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.TaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
class UseObserveTaskById(val taskRepository: TaskRepository) {

    operator fun invoke(id:Int): Flow<Resource<Task>> = flow {

        try {
            emit(Resource.Loading())
            val task = taskRepository.observeTaskById(id).toTask()
            emit(Resource.Success(task))
        }catch (e:Exception){
            Log.d("use_get_task_by_id", e.toString())
            emit(Resource.Error("some"))
        }

    }

}