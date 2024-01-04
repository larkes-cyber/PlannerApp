package com.example.mywaycompose.domain.usecase.main_task

import android.util.Log
import com.example.mywaycompose.domain.mapper.toMainTask
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class UseObserveMainTaskById(
    private val mainTaskRepository: MainTaskRepository
) {
    operator fun invoke(id:String): Flow<Resource<MainTask>> = flow{

        try {
            emit(Resource.Loading())
            val mainTask = mainTaskRepository.getMainTaskById(id)
            emit(Resource.Success(mainTask.toMainTask()))
        }catch (e:Exception){
            emit(Resource.Error("Непредвиденная ошибка"))
        }

    }
}