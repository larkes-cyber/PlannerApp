package com.example.mywaycompose.data.repository

import android.util.Log
import com.example.mywaycompose.data.local.database.entity.TaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask
import com.example.mywaycompose.data.source.task.TaskFirebaseDataSource
import com.example.mywaycompose.data.source.task.TasksRoomDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.domain.mapper.toFirebaseTask
import com.example.mywaycompose.domain.mapper.toTask
import com.example.mywaycompose.domain.mapper.toTaskEntity
import com.example.mywaycompose.domain.repository.TaskRepository
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.Constants.NO_INTERNET
import com.example.mywaycompose.utils.Constants.TASKS_KIND
import com.example.mywaycompose.utils.InternetConnectionService
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DataTaskRepository(
    private val taskFirebaseDataSource: TaskFirebaseDataSource,
    private val tasksRoomDataSource: TasksRoomDataSource,
    private val internetConnectionService: InternetConnectionService,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineScope: CoroutineScope
): TaskRepository {
    override suspend fun insertTask(task: TaskEntity) {
        if(internetConnectionService.isOnline()){
            taskFirebaseDataSource.pushTask(task.toTask().toFirebaseTask())
            task.online_sync = true
        }else{
            task.online_sync = false
        }
        tasksRoomDataSource.insertTask(task)
    }


    override suspend fun observeTasksByDate(date: String): List<TaskEntity> = tasksRoomDataSource.observeTasksByDate(date)

    override suspend fun observeTaskById(id: Int): TaskEntity = tasksRoomDataSource.observeTaskById(id)

    override suspend fun checkSameTask(time: String, date: String): Boolean = tasksRoomDataSource.checkSameTask(time = time, date = date)

    override suspend fun deleteTask(task: TaskEntity) {
        if(internetConnectionService.isOnline()){
            taskFirebaseDataSource.deleteTask(task.toTask().toFirebaseTask())
            tasksRoomDataSource.deleteTask(task)
        }else{
            task.hide = true
            tasksRoomDataSource.insertTask(task)
        }
    }

    override suspend fun observeAllTasks(): List<TaskEntity> = tasksRoomDataSource.observeAllTasks()

    override suspend fun cleanTasksDatabase() = tasksRoomDataSource.cleanTasksDatabase()
    override fun syncTasks():Flow<Resource<String>> = callbackFlow {
        if(internetConnectionService.isOnline()){
            val allTasks = observeAllTasks()
            allTasks.forEach {
                if(it.hide){
                    tasksRoomDataSource.deleteTask(it)
                    taskFirebaseDataSource.deleteTask(it.toTask().toFirebaseTask())
                }
                if(!it.hide && !it.online_sync){
                    taskFirebaseDataSource.pushTask(it.toTask().toFirebaseTask())
                }
            }
            val serverTasks = userRemoteDataSource.getAppData<FirebaseTask>(TASKS_KIND)
            tasksRoomDataSource.cleanTasksDatabase()
            serverTasks.onEach {tasks ->
                tasks.forEach {
                    val task = it.toTask()
                    task.online_sync = true
                    tasksRoomDataSource.insertTask(task.toTaskEntity())
                }
                trySend(Resource.Success("OK"))
                channel.close()
            }.launchIn(coroutineScope)
        }else{
            trySend(Resource.Error(NO_INTERNET))
        }
        awaitClose {   }
    }

    override fun pushTaskFirebase(task: FirebaseTask) = taskFirebaseDataSource.pushTask(task = task)

    override fun deleteTaskFirebase(task: FirebaseTask) = taskFirebaseDataSource.deleteTask(task = task)


}
