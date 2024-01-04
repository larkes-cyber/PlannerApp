package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseSyncMainTasks(
    private val mainTaskRepository: MainTaskRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke(): Flow<Resource<String>> = callbackFlow{

        try {
            trySend(Resource.Loading())
            val result = mainTaskRepository.syncMainTasks()
            result.onEach {res ->
                when(res){
                    is Resource.Error -> {
                        trySend(Resource.Error(res.message!!))
                        channel.close()
                    }
                    is Resource.Success -> {
                        trySend(Resource.Success(res.data!!))
                        channel.close()
                    }
                    else -> {channel.close()}
                }
            }.launchIn(coroutineScope)
        }catch (e:Exception){
            trySend(Resource.Error(e.message.toString()))
            channel.close()
        }

        awaitClose {  }

    }

}