package com.example.mywaycompose.domain.usecase.visual_task

import android.util.Log
import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UseGetVisualTasksFromFirebase(
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
) {

    operator fun invoke(): Flow<Resource<List<VisualTask>>> = callbackFlow {
        trySend(Resource.Loading())
        try {
            val tasks =
                userRepository.getAppData<FirebaseVisualTask>(Constants.VISUAL_TASK_KIND)
            var open_flow: ProducerScope<Resource<List<VisualTask>>>? = null
            tasks.onEach { tasks ->
                open_flow = this
                trySend(Resource.Success(tasks.map { it.toVisualTask() }))
            }.launchIn(coroutineScope)

            awaitClose { open_flow!!.close() }

        } catch (e: Exception) {
            Log.d("sdfdsfsdfsd",e.toString())
            trySend(Resource.Error(e.toString()))
        }
    }

}