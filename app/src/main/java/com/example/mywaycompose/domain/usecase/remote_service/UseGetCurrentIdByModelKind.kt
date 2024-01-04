package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseGetCurrentIdByModelKind(
    private val serviceRepository: ServiceRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke(kind:String):Flow<Resource<Int>> = callbackFlow {

        try {
            val id = serviceRepository.getCurrentIdBySomeModel(kind)
            var open_flow: ProducerScope<Resource<Int>>? = null
            id.onEach {
                open_flow = this
                trySend(Resource.Success(it))
            }.launchIn(coroutineScope)

            awaitClose{open_flow!!.close()}

        }catch (e:Exception){
            trySend(Resource.Error(e.toString()))
        }

    }

}