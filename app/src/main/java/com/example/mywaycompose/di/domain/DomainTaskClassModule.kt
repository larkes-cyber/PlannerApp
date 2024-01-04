package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.TaskClassRepository
import com.example.mywaycompose.domain.usecase.task_class.UseInsertTaskClass
import com.example.mywaycompose.domain.usecase.task_class.UseObserveTaskClasses
import com.example.mywaycompose.domain.usecase.task_class.UseSyncTaskClasses
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object DomainTaskClassModule {

    @Provides
    fun provideUseObserveTaskClasses(
        taskClassRepository: TaskClassRepository
    ):UseObserveTaskClasses{
        return UseObserveTaskClasses(taskClassRepository)
    }

    @Provides
    fun provideUseInsertTaskClass(
        taskClassRepository: TaskClassRepository
    ):UseInsertTaskClass{
        return UseInsertTaskClass(taskClassRepository)
    }

    @Provides
    fun provideUseSyncTaskClasses(
        taskClassRepository: TaskClassRepository,
        coroutineScope: CoroutineScope
    ):UseSyncTaskClasses{
        return UseSyncTaskClasses(
            taskClassRepository = taskClassRepository,
            coroutineScope = coroutineScope
        )
    }

}