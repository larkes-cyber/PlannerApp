package com.example.mywaycompose.data.repository

import android.util.Log
import com.example.mywaycompose.data.local.database.entity.TaskClassEntity
import com.example.mywaycompose.data.local.database.entity.TaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea
import com.example.mywaycompose.data.remote.firebase_model.FirebaseTaskClass
import com.example.mywaycompose.data.source.task_class.TaskClassDatabaseDataSource
import com.example.mywaycompose.data.source.task_class.TaskClassRemoteDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.domain.mapper.*
import com.example.mywaycompose.domain.model.TaskClass
import com.example.mywaycompose.domain.repository.TaskClassRepository
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.InternetConnectionService
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DataTaskClassRepository(
    private val taskClassDatabaseDataSource: TaskClassDatabaseDataSource,
    private val taskClassRemoteDataSource: TaskClassRemoteDataSource,
    private val internetConnectionService: InternetConnectionService,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineScope: CoroutineScope
):TaskClassRepository {
    override suspend fun insertTaskClass(taskClass: TaskClassEntity) {

        if(internetConnectionService.isOnline()){
            taskClassRemoteDataSource.pushTaskClass(taskClass.toTaskClass().toFirebaseTaskClass())
            taskClass.online_sync = true
        }else taskClass.online_sync = false
        taskClassDatabaseDataSource.insertTaskClass(taskClass)
    }

    override suspend fun observeTaskClasses(): List<TaskClassEntity> {
        return taskClassDatabaseDataSource.observeTaskClasses()
    }

    override suspend fun syncTaskClasses():Flow<Resource<String>> = callbackFlow {
        Log.d("class_task_debug_log", "started")
        if(internetConnectionService.isOnline()){
            val allClasses = observeTaskClasses()
            allClasses.forEach {
                if(!it.online_sync){
                    taskClassRemoteDataSource.pushTaskClass(it.toTaskClass().toFirebaseTaskClass())
                }
            }
            val serverClasses = userRemoteDataSource.getAppData<FirebaseTaskClass>(Constants.TASK_CLASS_DATABASE)
            taskClassDatabaseDataSource.nukeDatabase()
            serverClasses.onEach {taskClasses ->
                Log.d("class_task_debug_log","recieve flow result")
                taskClasses.forEach {
                    val taskClass = it.toTaskClass()
                    taskClass.online_sync = true
                    taskClassDatabaseDataSource.insertTaskClass(taskClass.toTaskClassEntity())
                }
                Log.d("class_task_debug_log","done")
                trySend(Resource.Success("OK"))
                channel.close()
            }.launchIn(coroutineScope)
        }else{
            Log.d("class_task_debug_log", "no internet")
            trySend(Resource.Error(Constants.NO_INTERNET))
        }
        awaitClose {   }
    }

    override suspend fun deleteClass(taskClass: TaskClassEntity) {
        if(internetConnectionService.isOnline()){
            taskClassRemoteDataSource.deleteTaskClass(taskClass.toTaskClass().toFirebaseTaskClass())
            taskClassDatabaseDataSource.deleteTaskClass(taskClass)
        }else{
            taskClass.visible = false
            taskClassDatabaseDataSource.insertTaskClass(taskClass)
        }
    }

    override suspend fun findClassByVsTaskId(id: String): TaskClassEntity? {
        return taskClassDatabaseDataSource.findTaskClassByVsTaskId(id)
    }

}