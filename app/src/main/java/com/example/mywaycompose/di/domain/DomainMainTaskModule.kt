package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.repository.VisualTaskRepository
import com.example.mywaycompose.domain.usecase.*
import com.example.mywaycompose.domain.usecase.main_task.UseInsertMainTask
import com.example.mywaycompose.domain.usecase.main_task.UseInsertShortMainTask
import com.example.mywaycompose.domain.usecase.main_task.UseDeleteAllMainTasks
import com.example.mywaycompose.domain.usecase.main_task.UseDeleteMainTask
import com.example.mywaycompose.domain.usecase.main_task.UseObserveAllMainTasks
import com.example.mywaycompose.domain.usecase.main_task.UseObserveMainTaskById
import com.example.mywaycompose.domain.usecase.main_task.UseObserveMainTaskFirebase
import com.example.mywaycompose.domain.usecase.main_task.UsePushMainTaskImageFirebase
import com.example.mywaycompose.domain.usecase.main_task.UsePushMainTaskFirebase
import com.example.mywaycompose.domain.usecase.main_task.UseSaveFirebaseImage
import com.example.mywaycompose.domain.usecase.main_task.UseSyncMainTasks
import com.example.mywaycompose.domain.usecase.main_task.UseUpdateMainTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object DomainMainTaskModule {
    @Provides
    fun provideUseAddMainTask(
        mainTaskRepository: MainTaskRepository,
        serviceRepository: ServiceRepository
    ): UseInsertMainTask {
        return UseInsertMainTask(
            mainTaskRepository = mainTaskRepository,
            serviceRepository = serviceRepository
        )
    }

    @Provides
    fun provideUsePushMainTaskToFirebase(
        mainTaskRepository: MainTaskRepository
    ): UsePushMainTaskFirebase {
        return UsePushMainTaskFirebase(mainTaskRepository)
    }

    @Provides
    fun provideUsePushMainTaskImageToFirebase(
        mainTaskRepository: MainTaskRepository,
        serviceRepository: ServiceRepository
    ): UsePushMainTaskImageFirebase {
        return UsePushMainTaskImageFirebase(mainTaskRepository,serviceRepository)
    }

    @Provides
    fun provideUseGetAllMainTasks(
        mainTaskRepository: MainTaskRepository
    ): UseObserveAllMainTasks {
        return UseObserveAllMainTasks(mainTaskRepository)
    }
    @Provides
    fun provideUseGetMainTaskById(
        mainTaskRepository: MainTaskRepository
    ): UseObserveMainTaskById {
        return UseObserveMainTaskById(mainTaskRepository)
    }

    @Provides
    fun provideUseDeleteMainTask(
        mainTaskRepository: MainTaskRepository,
        visualTaskRepository: VisualTaskRepository
    ): UseDeleteMainTask {
        return UseDeleteMainTask(
            mainTaskRepository,
            visualTaskRepository,
        )
    }
    @Provides
    fun provideUseUpdateMainTask(
        mainTaskRepository: MainTaskRepository,
        serviceRepository: ServiceRepository,
    ): UseUpdateMainTask {
        return UseUpdateMainTask(
            mainTaskRepository,
            serviceRepository
        )
    }
    @Provides
    fun provideUseDeleteAllMainTasks(
        serviceRepository: ServiceRepository,
        mainTaskRepository: MainTaskRepository
    ): UseDeleteAllMainTasks {
        return UseDeleteAllMainTasks(
            serviceRepository = serviceRepository,
            mainTaskRepository = mainTaskRepository
        )
    }

    @Provides
    fun provideUseAddMainTaskFromFirebase(
        mainTaskRepository: MainTaskRepository
    ): UseInsertShortMainTask {
        return UseInsertShortMainTask(mainTaskRepository)
    }

    @Provides
    fun provideUseGetMainTasksFromFirebase(
        userRepository: UserRepository,
        coroutineScope: CoroutineScope
    ):UseObserveMainTaskFirebase{
        return UseObserveMainTaskFirebase(
            userRepository = userRepository,
            coroutineScope =  coroutineScope
        )
    }

    @Provides
    fun provideUseSaveImageFromFirebase(
        serviceRepository: ServiceRepository
    ):UseSaveFirebaseImage{
        return UseSaveFirebaseImage(
            serviceRepository = serviceRepository
        )
    }

    @Provides
    fun provideUseSyncMainTasks(
        mainTaskRepository: MainTaskRepository,
        coroutineScope: CoroutineScope
    ):UseSyncMainTasks{
        return UseSyncMainTasks(
            mainTaskRepository = mainTaskRepository,
            coroutineScope = coroutineScope
        )
    }
}