package com.example.mywaycompose.data.repository

import android.util.Log
import com.example.mywaycompose.data.local.database.entity.MainTaskEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea
import com.example.mywaycompose.data.remote.firebase_model.FirebaseMainTask
import com.example.mywaycompose.data.source.main_task.MainTaskFirebaseDataSource
import com.example.mywaycompose.data.source.main_task.MainTasksRoomDataSource
import com.example.mywaycompose.data.source.service.ServiceImageStorageDataSource
import com.example.mywaycompose.data.source.service.ServiceRemoteDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.domain.mapper.*
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.TaskClassRepository
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
import java.io.File

class DataMainTaskRepository(
    private val mainTasksRoomDataSource: MainTasksRoomDataSource,
    private val mainTaskFirebaseDataSource: MainTaskFirebaseDataSource,
    private val internetConnectionService: InternetConnectionService,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineScope: CoroutineScope,
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
    private val serviceImageStorageDataSource: ServiceImageStorageDataSource,
    private val visualTaskRepository: VisualTaskRepository,
    private val serviceRepository: ServiceRepository,
    private val taskClassRepository: TaskClassRepository
    ): MainTaskRepository {
    override suspend fun addMainTask(mainTask: MainTaskEntity) {
        if(internetConnectionService.isOnline()){
            mainTaskFirebaseDataSource.pushMainTask(mainTask.toMainTask().toFirebaseMainTask())
            mainTask.online_sync = true
        }
        mainTasksRoomDataSource.insertMainTask(mainTask)
    }

    override suspend fun getAllMainTasks(): List<MainTaskEntity> {
        return mainTasksRoomDataSource.observeAllMainTasks()
    }

    override suspend fun getMainTaskById(id:String): MainTaskEntity {
        return mainTasksRoomDataSource.observeMainTaskById(id)
    }

    override suspend fun getMainTaskBytTitle(title: String):MainTaskEntity {
        return mainTasksRoomDataSource.observeMainTaskByTitle(title)
    }

    override suspend fun deleteMainTask(task: MainTaskEntity) {
        if(task.imageSrc != null) serviceRepository.deleteFile(serviceRepository.getImageFileById(task.id))
        if(internetConnectionService.isOnline()){
            if(task.imageSrc != null) serviceRepository.deletePhotoById(task.id)
            mainTaskFirebaseDataSource.deleteMainTask(task.toMainTask().toFirebaseMainTask())
            mainTasksRoomDataSource.deleteMainTask(task)
            visualTaskRepository.getAllVisualTasksByMainTaskId(task.id).forEach {
                visualTaskRepository.deleteVisualTaskFirebase(it.toVisualTask().toFirebaseVisualTask())
                val classTask = taskClassRepository.findClassByVsTaskId(it.id)
                if(classTask != null){
                    taskClassRepository.deleteClass(classTask)
                }
            }
        }else{
            task.hide = true
            updateMainTask(task)
        }
        visualTaskRepository.getAllVisualTasksByMainTaskId(task.id).forEach {
            visualTaskRepository.deleteVisualTask(it)
        }
    }

    override suspend fun updateMainTask(task: MainTaskEntity) {
        mainTasksRoomDataSource.deleteMainTask(task)
        val vsTask = visualTaskRepository.findVisualTaskByParentId("-1")
        vsTask.text = task.title
        visualTaskRepository.deleteVisualTask(vsTask)
        if(internetConnectionService.isOnline()){
            mainTaskFirebaseDataSource.pushMainTask(task.toMainTask().toFirebaseMainTask())
            visualTaskRepository.pushVisualTaskFirebase(vsTask.toVisualTask().toFirebaseVisualTask())
            vsTask.online_sync = true
            task.online_sync = true
        }else{
            vsTask.online_sync = false
            task.online_sync = false
        }
        visualTaskRepository.insertVisualTask(vsTask)
        mainTasksRoomDataSource.insertMainTask(task)
    }


    override fun pushMainTaskFirebase(mainTask: FirebaseMainTask) {
        mainTaskFirebaseDataSource.pushMainTask(mainTask)
    }

    override fun pushMainTaskImageFirebase(image: ByteArray, id: String) {
        mainTaskFirebaseDataSource.pushMainTaskImage(image, id)
    }

    override fun deleteMainTaskFirebase(mainTask: FirebaseMainTask) {
        mainTaskFirebaseDataSource.deleteMainTask(mainTask)
    }

    override fun syncMainTasks(): Flow<Resource<String>> = callbackFlow {
        if(internetConnectionService.isOnline()){



            val allMainTasks = getAllMainTasks()
            allMainTasks.forEach {
                if(it.hide){
                    mainTaskFirebaseDataSource.deleteMainTask(it.toMainTask().toFirebaseMainTask())
                    mainTasksRoomDataSource.deleteMainTask(it)
                    if(it.imageSrc != null) serviceRemoteDataSource.deletePhotoById(it.id)
                }
                if(!it.hide && !it.online_sync){
                    if(it.imageSrc != null) {
                        val image = serviceImageStorageDataSource.getImageFileById(it.id).readBytes()
                        mainTaskFirebaseDataSource.pushMainTaskImage(image = image, id = it.id)
                    }
                    mainTaskFirebaseDataSource.pushMainTask(it.toMainTask().toFirebaseMainTask())
                }
            }
            val serverMainTasks = userRemoteDataSource.getAppData<Pair<String, FirebaseMainTask>>(Constants.MAIN_TASKS_KIND)
            mainTasksRoomDataSource.nukeMainTasksDatabase()
            serverMainTasks.onEach {mainTasks ->
                mainTasks.forEach {
                    val mainTask = it.second.toMainTask()
                    mainTask.online_sync = true

                    serviceRepository.saveFirebaseImage(mainTask.id)
                    val file = serviceImageStorageDataSource.getImageFileById(mainTask.id)
                    mainTask.imageSrc = file.absolutePath

                    mainTasksRoomDataSource.insertMainTask(mainTask.toMainTaskEntity())
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

