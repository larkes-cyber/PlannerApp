package com.example.mywaycompose.domain.usecase.idea

import com.example.mywaycompose.domain.repository.IdeaRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseSyncIdeas(
    private val ideaRepository: IdeaRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke(): Flow<Resource<String>> = callbackFlow{

        try {
            trySend(Resource.Loading())
            val result = ideaRepository.syncIdeas()
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