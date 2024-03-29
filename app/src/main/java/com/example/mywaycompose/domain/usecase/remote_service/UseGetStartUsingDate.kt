package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseGetStartUsingDate(
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke():Flow<String> = callbackFlow{

        var open_flow:ProducerScope<String>? = null
        userRepository.observeFirstDate().onEach {
            open_flow = this
            trySend(it[0])
            this.close()
        }.launchIn(coroutineScope)

        awaitClose { open_flow!!.close() }
    }

}