package com.example.mywaycompose.domain.usecase.main_task

import android.util.Log
import com.example.mywaycompose.domain.mapper.toMainTask
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UseObserveAllMainTasks(
    private val mainTaskRepository: MainTaskRepository
) {

    operator fun invoke(): Flow<Resource<List<MainTask>>> = flow{
        try {
            emit(Resource.Loading())
            val tasks = mainTaskRepository.getAllMainTasks()
            emit(Resource.Success(tasks.map { it.toMainTask() }))
        }catch (e:Exception){
            Log.d("sdfsdfsdfsdfsdfsdf",e.toString())
//            emit(Resource.Error(e.toString()))
        }

    }

}