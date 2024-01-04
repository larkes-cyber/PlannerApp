package com.example.mywaycompose.domain.usecase.main_task

import android.net.Uri
import android.util.Log
import com.example.mywaycompose.domain.mapper.toMainTaskEntity
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class UseInsertMainTask(
    private val mainTaskRepository: MainTaskRepository,
    private val serviceRepository: ServiceRepository
    ) {

    suspend fun execute(mainTask: MainTask){
        Log.d("sdfsdfsdfsdfvvvv","##########")
        mainTaskRepository.addMainTask(mainTask.toMainTaskEntity())
    }

}