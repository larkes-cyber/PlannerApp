package com.example.mywaycompose.di.data


import com.example.mywaycompose.data.repository.*
import com.example.mywaycompose.data.repository.DataIdeaRepository

import com.example.mywaycompose.data.repository.DataMainTaskRepository
import com.example.mywaycompose.data.repository.DataProductTaskRepository
import com.example.mywaycompose.data.repository.DataServiceRepository

import com.example.mywaycompose.data.repository.DataTaskRepository
import com.example.mywaycompose.data.repository.DataUserRepository
import com.example.mywaycompose.data.repository.DataVisualTaskRepository
import com.example.mywaycompose.data.source.idea.IdeaFirebaseDataSource
import com.example.mywaycompose.data.source.idea.IdeasRoomDataSource
import com.example.mywaycompose.data.source.main_task.MainTaskFirebaseDataSource
import com.example.mywaycompose.data.source.main_task.MainTasksRoomDataSource
import com.example.mywaycompose.data.source.product_task.ProductTaskFirebaseDataSource
import com.example.mywaycompose.data.source.product_task.ProductTasksRoomDataSource
import com.example.mywaycompose.data.source.service.ServiceImageStorageDataSource
import com.example.mywaycompose.data.source.service.ServiceRemoteDataSource
import com.example.mywaycompose.data.source.service.ServiceSharedPreferenceDataSource
import com.example.mywaycompose.data.source.task.TaskFirebaseDataSource
import com.example.mywaycompose.data.source.task.TasksRoomDataSource
import com.example.mywaycompose.data.source.task_class.TaskClassDatabaseDataSource
import com.example.mywaycompose.data.source.task_class.TaskClassRemoteDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSource
import com.example.mywaycompose.data.source.visual_task.VisualTaskFirebaseDataSource
import com.example.mywaycompose.data.source.visual_task.VisualTaskRoomDataSource
import com.example.mywaycompose.domain.repository.*
import com.example.mywaycompose.domain.repository.IdeaRepository
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ProductTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository

import com.example.mywaycompose.domain.repository.TaskRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.repository.VisualTaskRepository
import com.example.mywaycompose.utils.InternetConnectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataTasksRepository(
        tasksRoomDataSource: TasksRoomDataSource,
        taskFirebaseDataSource: TaskFirebaseDataSource,
        internetConnectionService: InternetConnectionService,
        coroutineScope: CoroutineScope,
        userRemoteDataSource: UserRemoteDataSource
    ): TaskRepository {
        return DataTaskRepository(
            tasksRoomDataSource = tasksRoomDataSource,
            taskFirebaseDataSource = taskFirebaseDataSource,
            internetConnectionService = internetConnectionService,
            coroutineScope = coroutineScope,
            userRemoteDataSource = userRemoteDataSource
        )
    }


    @Provides
    fun provideLocalServiceRepository(
        serviceSharedPreferenceDataSource: ServiceSharedPreferenceDataSource,
        serviceImageStorageDataSource: ServiceImageStorageDataSource,
        serviceRemoteDataSource: ServiceRemoteDataSource,
        userSharedPreferenceDataSource: UserSharedPreferenceDataSource
    ): ServiceRepository {
        return DataServiceRepository(
            serviceImageStorageDataSource = serviceImageStorageDataSource,
            serviceSharedPreferenceDataSource = serviceSharedPreferenceDataSource,
            serviceRemoteDataSource = serviceRemoteDataSource,
            userSharedPreferenceDataSource = userSharedPreferenceDataSource
        )
    }





    @Provides
    fun provideDataMainTasksRepository(
        mainTasksRoomDataSource: MainTasksRoomDataSource,
        mainTaskFirebaseDataSource: MainTaskFirebaseDataSource,
        internetConnectionService: InternetConnectionService,
        userRemoteDataSource: UserRemoteDataSource,
        coroutineScope: CoroutineScope,
        serviceRemoteDataSource: ServiceRemoteDataSource,
        serviceImageStorageDataSource: ServiceImageStorageDataSource,
        visualTaskRepository: VisualTaskRepository,
        serviceRepository: ServiceRepository,
        taskClassRepository: TaskClassRepository
    ): MainTaskRepository {
        return DataMainTaskRepository(
            mainTasksRoomDataSource = mainTasksRoomDataSource,
            mainTaskFirebaseDataSource = mainTaskFirebaseDataSource,
            internetConnectionService = internetConnectionService,
            userRemoteDataSource = userRemoteDataSource,
            coroutineScope = coroutineScope,
            serviceRemoteDataSource = serviceRemoteDataSource,
            serviceImageStorageDataSource = serviceImageStorageDataSource,
            visualTaskRepository = visualTaskRepository,
            serviceRepository = serviceRepository,
            taskClassRepository = taskClassRepository
        )
    }


    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userSharedPreferenceDataSource: UserSharedPreferenceDataSource
    ): UserRepository = DataUserRepository(
        userRemoteDataSource = userRemoteDataSource,
        userSharedPreferenceDataSource = userSharedPreferenceDataSource
    )

    @Provides
    fun provideDataIdeasRepository(
        ideasRoomDataSource: IdeasRoomDataSource,
        ideaFirebaseDataSource: IdeaFirebaseDataSource,
        internetConnectionService: InternetConnectionService,
        coroutineScope: CoroutineScope,
        userRemoteDataSource: UserRemoteDataSource
    ): IdeaRepository {
        return DataIdeaRepository(
            ideasRoomDataSource = ideasRoomDataSource,
            ideaFirebaseDataSource = ideaFirebaseDataSource,
            internetConnectionService = internetConnectionService,
            coroutineScope = coroutineScope,
            userRemoteDataSource = userRemoteDataSource
        )
    }

    @Provides
    fun provideDataVisualTaskLocalRepository(
        visualTaskRoomDataSource: VisualTaskRoomDataSource,
        visualTaskFirebaseDataSource: VisualTaskFirebaseDataSource,
        internetConnectionService: InternetConnectionService,
        coroutineScope: CoroutineScope,
        userRemoteDataSource: UserRemoteDataSource
    ): VisualTaskRepository {
        return DataVisualTaskRepository(
            visualTaskRoomDataSource = visualTaskRoomDataSource,
            visualTaskFirebaseDataSource = visualTaskFirebaseDataSource,
            internetConnectionService = internetConnectionService,
            coroutineScope = coroutineScope,
            userRemoteDataSource = userRemoteDataSource
        )
    }

    @Provides
    fun provideLocalProductTasksRepository(
        productTasksRoomDataSource: ProductTasksRoomDataSource,
        productTaskFirebaseDataSource: ProductTaskFirebaseDataSource,
        internetConnectionService: InternetConnectionService,
        coroutineScope: CoroutineScope,
        userRemoteDataSource: UserRemoteDataSource
    ): ProductTaskRepository {
        return DataProductTaskRepository(
            productTasksRoomDataSource = productTasksRoomDataSource,
            productTaskFirebaseDataSource = productTaskFirebaseDataSource,
            internetConnectionService = internetConnectionService,
            coroutineScope = coroutineScope,
            userRemoteDataSource = userRemoteDataSource
        )
    }

    @Provides
    fun provideTaskClassRepository(
        taskClassDatabaseDataSource: TaskClassDatabaseDataSource,
        taskClassRemoteDataSource: TaskClassRemoteDataSource,
        internetConnectionService: InternetConnectionService,
        coroutineScope: CoroutineScope,
        userRemoteDataSource: UserRemoteDataSource
    ):TaskClassRepository{
        return DataTaskClassRepository(
            taskClassDatabaseDataSource = taskClassDatabaseDataSource,
            taskClassRemoteDataSource = taskClassRemoteDataSource,
            internetConnectionService = internetConnectionService,
            coroutineScope = coroutineScope,
            userRemoteDataSource = userRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideCoroutineContext():CoroutineContext{
        return Job()
    }

    @Singleton
    @Provides
    fun provideCoroutineScope(
        context:CoroutineContext
    ):CoroutineScope{
        return CoroutineScope(context + SupervisorJob())
    }




}