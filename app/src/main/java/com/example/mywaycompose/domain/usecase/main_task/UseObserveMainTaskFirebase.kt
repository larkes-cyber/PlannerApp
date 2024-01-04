package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseMainTask
import com.example.mywaycompose.domain.mapper.toMainTask
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.utils.Constants.MAIN_TASKS_KIND
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseObserveMainTaskFirebase(
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke(): Flow<Resource<List<Pair<String, MainTask>>>> = callbackFlow {

        trySend(Resource.Loading())
        try {
            val tasks = userRepository.getAppData<Pair<String, FirebaseMainTask>>(MAIN_TASKS_KIND)
            var open_flow: ProducerScope<Resource<List<Pair<String, MainTask>>>>? = null
            tasks.onEach {tasks ->
                open_flow = this
                trySend(Resource.Success(tasks.map { Pair(it.first, it.second.toMainTask())}))
            }.launchIn(coroutineScope)

            awaitClose{open_flow!!.close()}

        }catch (e:Exception){
            trySend(Resource.Error(e.toString()))
        }


    }

}