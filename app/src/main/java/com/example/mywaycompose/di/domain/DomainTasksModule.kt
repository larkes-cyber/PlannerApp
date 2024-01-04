package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.TaskRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.usecase.*
import com.example.mywaycompose.domain.usecase.local_service.UseNukeDatabase
import com.example.mywaycompose.domain.usecase.task.UseInsertTask
import com.example.mywaycompose.domain.usecase.task.UseDeleteTask
import com.example.mywaycompose.domain.usecase.task.UseObserveTaskById
import com.example.mywaycompose.domain.usecase.task.UseObserveTasks
import com.example.mywaycompose.domain.usecase.task.UseCheckSameTasks
import com.example.mywaycompose.domain.usecase.task.UseObserveTasksFirebase
import com.example.mywaycompose.domain.usecase.task.UsePushTaskFirebase
import com.example.mywaycompose.domain.usecase.task.UseSyncTasks
import com.example.mywaycompose.domain.usecase.task.UseToCompleteTask
import com.example.mywaycompose.domain.usecase.task.UseUpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object  DomainTasksModule {

    @Provides
    fun provideUseAddTask(taskRepository: TaskRepository): UseInsertTask {
        return UseInsertTask(taskRepository)
    }

    @Provides
    fun provideUsePushTaskToFirebase(
        taskRepository: TaskRepository
    ): UsePushTaskFirebase {
        return UsePushTaskFirebase(taskRepository)
    }

    @Provides
    fun provideUseGetTasks(
        taskRepository: TaskRepository,
        serviceRepository: ServiceRepository
    ): UseObserveTasks {
        return UseObserveTasks(
            taskRepository,
            serviceRepository
        )
    }


    @Provides
    fun provideUseGetTaskById(taskRepository: TaskRepository): UseObserveTaskById {
        return UseObserveTaskById(taskRepository)
    }

    @Provides
    fun provideUseUpdateTask(taskRepository: TaskRepository): UseUpdateTask {
        return UseUpdateTask(taskRepository)
    }

    @Provides
    fun provideUseIsThereTheSameTask(taskRepository: TaskRepository): UseCheckSameTasks {
        return UseCheckSameTasks(taskRepository)
    }

    @Provides
    fun provideUseDeleteTask(taskRepository: TaskRepository): UseDeleteTask {
        return UseDeleteTask(taskRepository)
    }

    @Provides
    fun provideUseToCompleteTask(
        taskRepository: TaskRepository
    ): UseToCompleteTask {
        return UseToCompleteTask(
           taskRepository = taskRepository
        )
    }


    @Provides
    fun provideUseDeleteAllTables(
        taskRepository: TaskRepository
    ): UseNukeDatabase {
        return UseNukeDatabase(taskRepository)
    }


    @Provides
    fun provideUseGetTaskByTaskFromFirebase(
        coroutineScope: CoroutineScope,
        userRepository: UserRepository
    ):UseObserveTasksFirebase{
        return UseObserveTasksFirebase(userRepository, coroutineScope)
    }

    @Provides
    fun provideSyncTasks(
        taskRepository: TaskRepository,
        coroutineScope: CoroutineScope
    ):UseSyncTasks{
         return UseSyncTasks(taskRepository, coroutineScope)
    }

}