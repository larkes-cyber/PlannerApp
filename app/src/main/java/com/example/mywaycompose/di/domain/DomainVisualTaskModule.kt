package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.TaskClassRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.repository.VisualTaskRepository
import com.example.mywaycompose.domain.usecase.local_service.UseGetActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.local_service.UsePutActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.remote_service.UsePutActuallyVisualTaskIdToFirebase
import com.example.mywaycompose.domain.usecase.visual_task.UseAddNewDependNode
import com.example.mywaycompose.domain.usecase.visual_task.UseDeleteVisualTask
import com.example.mywaycompose.domain.usecase.visual_task.UseFindVisualMainTask
import com.example.mywaycompose.domain.usecase.visual_task.UseGetAllVisualTasks
import com.example.mywaycompose.domain.usecase.visual_task.UseGetAllVisualTasksFromDatabase
import com.example.mywaycompose.domain.usecase.visual_task.UseGetVisualTasksFromFirebase
import com.example.mywaycompose.domain.usecase.visual_task.UseInsertVisualTaskToDatabase
import com.example.mywaycompose.domain.usecase.visual_task.UsePushVisualTaskToFirebase
import com.example.mywaycompose.domain.usecase.visual_task.UseSyncVisualTasks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object DomainVisualTaskModule {

    @Provides
    fun provideUseInsertVisualTaskToDatabase(
        visualTaskRepository: VisualTaskRepository,
        usePushVisualTaskToFirebase: UsePushVisualTaskToFirebase
    ):UseInsertVisualTaskToDatabase{
        return UseInsertVisualTaskToDatabase(visualTaskRepository,usePushVisualTaskToFirebase)
    }

    @Provides
    fun provideUseGetAllVisualTasksFromDatabase(visualTaskRepository: VisualTaskRepository):UseGetAllVisualTasksFromDatabase{
        return UseGetAllVisualTasksFromDatabase(visualTaskRepository)
    }

    @Provides
    fun provideUsePutActuallyVisualTaskId(serviceRepository: ServiceRepository):UsePutActuallyVisualTaskId{
        return UsePutActuallyVisualTaskId(serviceRepository)
    }
    @Provides
    fun provideUseGetActuallyVisualTaskId(serviceRepository: ServiceRepository):UseGetActuallyVisualTaskId{
        return UseGetActuallyVisualTaskId(serviceRepository)
    }
    @Provides
    fun provideUsePutActuallyVisualTaskIdToFirebase(serviceRepository: ServiceRepository):UsePutActuallyVisualTaskIdToFirebase{
        return UsePutActuallyVisualTaskIdToFirebase(serviceRepository)
    }

    @Provides
    fun provideUseGetVisualTasksFromFirebase(
        userRepository: UserRepository,
        coroutineScope: CoroutineScope
    ):UseGetVisualTasksFromFirebase{
        return UseGetVisualTasksFromFirebase(userRepository, coroutineScope)
    }

    @Provides
    fun provideUseGetVisualTasks(
        visualTaskRepository: VisualTaskRepository
    ):UseGetAllVisualTasks{
        return UseGetAllVisualTasks(visualTaskRepository)
    }

    @Provides
    fun provideUseAddNewDependNode(
        visualTaskRepository: VisualTaskRepository
    ):UseAddNewDependNode{
        return UseAddNewDependNode(visualTaskRepository)
    }

    @Provides
    fun provideUseFindVisualMainTask(
        visualTaskRepository: VisualTaskRepository
    ):UseFindVisualMainTask{
        return UseFindVisualMainTask(visualTaskRepository)
    }

    @Provides
    fun provideUseDeleteVisualTask(
        visualTaskRepository: VisualTaskRepository,
        useGetAllVisualTasks: UseGetAllVisualTasks,
        taskClassRepository: TaskClassRepository
    ):UseDeleteVisualTask{
        return UseDeleteVisualTask(
            visualTaskRepository = visualTaskRepository,
            useGetAllVisualTasks = useGetAllVisualTasks,
            taskClassRepository = taskClassRepository
        )
    }
    @Provides
    fun provideUsePushVisualTaskToFirebase(visualTaskRepository: VisualTaskRepository): UsePushVisualTaskToFirebase {
        return UsePushVisualTaskToFirebase(visualTaskRepository)
    }
    @Provides
    fun provideUseSyncVisualTasks(visualTaskRepository: VisualTaskRepository, coroutineScope: CoroutineScope):UseSyncVisualTasks{
        return UseSyncVisualTasks(
            visualTaskRepository = visualTaskRepository,
            coroutineScope = coroutineScope
        )
    }
}