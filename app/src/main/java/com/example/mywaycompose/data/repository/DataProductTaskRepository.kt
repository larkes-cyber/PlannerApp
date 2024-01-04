package com.example.mywaycompose.data.repository

import com.example.mywaycompose.data.local.database.entity.ProductTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseProductTask
import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask
import com.example.mywaycompose.data.source.product_task.ProductTaskFirebaseDataSource
import com.example.mywaycompose.data.source.product_task.ProductTasksRoomDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.domain.mapper.toFirebaseProductTask
import com.example.mywaycompose.domain.mapper.toFirebaseVisualTask
import com.example.mywaycompose.domain.mapper.toProductTask
import com.example.mywaycompose.domain.mapper.toProductTaskEntity
import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTaskEntity
import com.example.mywaycompose.domain.repository.ProductTaskRepository
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.InternetConnectionService
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DataProductTaskRepository(
    private val productTasksRoomDataSource: ProductTasksRoomDataSource,
    private val productTaskFirebaseDataSource: ProductTaskFirebaseDataSource,
    private val internetConnectionService: InternetConnectionService,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineScope: CoroutineScope
): ProductTaskRepository {
    override suspend fun insertProductTask(productTaskEntity: ProductTaskEntity) {
        if(internetConnectionService.isOnline()){
            productTaskFirebaseDataSource.pushProductTask(productTaskEntity.toProductTask().toFirebaseProductTask())
            productTaskEntity.online_sync = true
        }else{
            productTaskEntity.online_sync = false
        }
        productTasksRoomDataSource.insertProductTask(productTaskEntity)
    }

    override suspend fun observeProductTasks(): List<ProductTaskEntity> {
        return productTasksRoomDataSource.observeProductTasks()
    }

    override suspend fun deleteProductTask(productTaskEntity: ProductTaskEntity) {
        if(internetConnectionService.isOnline()){
            productTasksRoomDataSource.deleteProductTask(productTaskEntity)
            productTaskFirebaseDataSource.deleteProductTask(productTaskEntity.toProductTask().toFirebaseProductTask())
        }else{
            productTaskEntity.hide = true
            productTasksRoomDataSource.insertProductTask(productTaskEntity)
        }

    }

    override fun pushProductTaskFirebase(productTask: FirebaseProductTask) {
        productTaskFirebaseDataSource.pushProductTask(productTask)
    }

    override fun deleteProductTaskFirebase(productTask: FirebaseProductTask) {
        productTaskFirebaseDataSource.deleteProductTask(productTask)
    }

    override fun syncProductTasks(): Flow<Resource<String>> = callbackFlow {
        if(internetConnectionService.isOnline()){

            val allProductTasks = observeProductTasks()
            allProductTasks.forEach {
                if(it.hide){
                    productTaskFirebaseDataSource.deleteProductTask(it.toProductTask().toFirebaseProductTask())
                    productTasksRoomDataSource.deleteProductTask(it)
                }
                if(!it.hide && !it.online_sync){
                    productTaskFirebaseDataSource.pushProductTask(it.toProductTask().toFirebaseProductTask())
                }
            }
            val serverVisualTasks = userRemoteDataSource.getAppData<FirebaseProductTask>(Constants.PRODUCT_TASKS_KIND)
            productTasksRoomDataSource.nukeProductTasksDatabase()
            serverVisualTasks.onEach { productTasks ->
                productTasks.forEach {
                    val productTask = it.toProductTask()
                    productTask.online_sync = true
                    productTasksRoomDataSource.insertProductTask(productTask.toProductTaskEntity())
                }
                trySend(Resource.Success("OK"))
                channel.close()
            }.launchIn(coroutineScope)
        }else{
            trySend(Resource.Error(Constants.NO_INTERNET))
        }
        awaitClose {   }
    }


}