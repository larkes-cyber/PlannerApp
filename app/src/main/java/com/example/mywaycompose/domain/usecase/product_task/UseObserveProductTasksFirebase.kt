package com.example.mywaycompose.domain.usecase.product_task

import android.util.Log
import com.example.mywaycompose.data.remote.firebase_model.FirebaseProductTask
import com.example.mywaycompose.domain.mapper.toProductTask
import com.example.mywaycompose.domain.model.ProductTask
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

class UseObserveProductTasksFirebase(
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
    ) {


    operator fun invoke(): Flow<Resource<List<ProductTask>>> = callbackFlow {

        trySend(Resource.Loading())
        try {
            val tasks = userRepository.getAppData<FirebaseProductTask>(Constants.PRODUCT_TASKS_KIND)
            var open_flow: ProducerScope<Resource<List<ProductTask>>>? = null
            tasks.onEach {
                open_flow = this
                Log.d("sfsdfsdf"," e.toString()")
                trySend(Resource.Success(it.map { it.toProductTask()}))
            }.launchIn(coroutineScope)

            awaitClose{open_flow!!.close()}

        }catch (e:Exception){
            Log.d("sfsdfsdf", e.toString())
            trySend(Resource.Error(e.toString()))
        }


    }

}