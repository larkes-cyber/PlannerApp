package com.example.mywaycompose.data.repository

import com.example.mywaycompose.data.local.database.entity.VisualTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.data.source.visual_task.VisualTaskFirebaseDataSource
import com.example.mywaycompose.data.source.visual_task.VisualTaskRoomDataSource
import com.example.mywaycompose.domain.mapper.toFirebaseVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTask
import com.example.mywaycompose.domain.mapper.toVisualTaskEntity
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.InternetConnectionService
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DataVisualTaskRepository(
    private val visualTaskRoomDataSource: VisualTaskRoomDataSource,
    private val visualTaskFirebaseDataSource: VisualTaskFirebaseDataSource,
    private val internetConnectionService: InternetConnectionService,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineScope: CoroutineScope
): VisualTaskRepository {
    override suspend fun insertVisualTask(visualTask: VisualTaskEntity) {
        if(internetConnectionService.isOnline()){
            visualTaskFirebaseDataSource.pushVisualTask(visualTask.toVisualTask().toFirebaseVisualTask())
            visualTask.online_sync = true
        }else{
            visualTask.online_sync = false
        }
        visualTaskRoomDataSource.insertVisualTask(visualTask)
    }

    override suspend fun getAllVisualTasksByMainTaskId(id:String): List<VisualTaskEntity> {
        return visualTaskRoomDataSource.observeVisualTasksByMainTaskId(id)
    }

    override suspend fun updateVisualTask(visualTask: VisualTaskEntity) {
        visualTaskRoomDataSource.insertVisualTask(visualTask)
    }

    override suspend fun getVisualTaskById(id: String): VisualTaskEntity {
        return visualTaskRoomDataSource.observeVisualTaskById(id)
    }

    override suspend fun getAllVisualTasks(): List<VisualTaskEntity> {
        return visualTaskRoomDataSource.observeAllVisualTasks()
    }

    override suspend fun findVisualMainTask(id: String): VisualTaskEntity {
        return visualTaskRoomDataSource.observeVisualTaskById(id)
    }
    override suspend fun deleteVisualTask(visualTask: VisualTaskEntity) {
        if(internetConnectionService.isOnline()){
            visualTaskRoomDataSource.deleteVisualTask(visualTask)
            visualTaskFirebaseDataSource.deleteVisualTask(visualTask.toVisualTask().toFirebaseVisualTask())
        }else{
            visualTask.hide = true
            visualTaskRoomDataSource.insertVisualTask(visualTask)
        }
    }

    override fun pushVisualTaskFirebase(visualTask: FirebaseVisualTask) {
        visualTaskFirebaseDataSource.pushVisualTask(visualTask)
    }

    override fun deleteVisualTaskFirebase(visualTask: FirebaseVisualTask) {
        visualTaskFirebaseDataSource.deleteVisualTask(visualTask)
    }

    override fun syncVisualTasks(): Flow<Resource<String>> = callbackFlow {
        if(internetConnectionService.isOnline()){

            val allVisualTasks = getAllVisualTasks()
            allVisualTasks.forEach {
                if(it.hide){
                    visualTaskFirebaseDataSource.deleteVisualTask(it.toVisualTask().toFirebaseVisualTask())
                    visualTaskRoomDataSource.deleteVisualTask(it)
                }
                if(!it.hide && !it.online_sync){
                    visualTaskFirebaseDataSource.pushVisualTask(it.toVisualTask().toFirebaseVisualTask())
                }
            }
            val serverVisualTasks = userRemoteDataSource.getAppData<FirebaseVisualTask>(Constants.VISUAL_TASK_KIND)
            visualTaskRoomDataSource.nukeVisualTaskDatabase()
            serverVisualTasks.onEach { visualTasks ->
                visualTasks.forEach {
                    val visualTask = it.toVisualTask()
                    visualTask.online_sync = true
                    visualTaskRoomDataSource.insertVisualTask(visualTask.toVisualTaskEntity())
                }
                trySend(Resource.Success("OK"))
                channel.close()
            }.launchIn(coroutineScope)
        }else{
            trySend(Resource.Error(Constants.NO_INTERNET))
        }
        awaitClose {   }
    }

    override suspend fun findVisualTaskByParentId(id: String): VisualTaskEntity {
        return visualTaskRoomDataSource.findVisualTaskByParentId(id)
    }
}



