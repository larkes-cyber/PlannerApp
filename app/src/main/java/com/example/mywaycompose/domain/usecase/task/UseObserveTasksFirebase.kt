package com.example.mywaycompose.domain.usecase.task

import android.util.Log
import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask
import com.example.mywaycompose.domain.mapper.toTask
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.utils.Constants.TASKS_KIND
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseObserveTasksFirebase(
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke(): Flow<Resource<List<Task>>> = callbackFlow {
        trySend(Resource.Loading())
        try {
            val tasks = userRepository.getAppData<FirebaseTask>(TASKS_KIND)
            var open_flow: ProducerScope<Resource<List<Task>>>? = null
            tasks.onEach {tasks ->
                open_flow = this
                trySend(Resource.Success(tasks.map { it.toTask() }))
            }.launchIn(coroutineScope)

            awaitClose{open_flow!!.close()}

        }catch (e:Exception){
            trySend(Resource.Error(e.toString()))
        }


    }

}