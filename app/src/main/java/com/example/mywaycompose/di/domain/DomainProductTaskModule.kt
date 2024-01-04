package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.ProductTaskRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.usecase.product_task.UseDeleteProductTask
import com.example.mywaycompose.domain.usecase.product_task.UseInsertProductTask
import com.example.mywaycompose.domain.usecase.product_task.UseObserveProductTasks
import com.example.mywaycompose.domain.usecase.product_task.UseObserveProductTasksFirebase
import com.example.mywaycompose.domain.usecase.product_task.UsePushProductTaskFirebase
import com.example.mywaycompose.domain.usecase.product_task.UseSyncProductTasks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object DomainProductTaskModule {

    @Provides
    fun provideUseDeleteProductTask(
        productTaskRepository: ProductTaskRepository
    ):UseDeleteProductTask{
        return UseDeleteProductTask(
            productTaskRepository = productTaskRepository
        )
    }

    @Provides
    fun provideUseObserveProductTasks(
        productTaskRepository: ProductTaskRepository
    ):UseObserveProductTasks{
        return UseObserveProductTasks(
            productTaskRepository = productTaskRepository
        )
    }

    @Provides
    fun provideUseInsertProductTask(
        productTaskRepository: ProductTaskRepository
    ):UseInsertProductTask{
        return UseInsertProductTask(
            productTasksRepository = productTaskRepository
        )
    }

    @Provides
    fun provideUsePushProductTaskToFirebase(productTaskRepository: ProductTaskRepository): UsePushProductTaskFirebase {
        return UsePushProductTaskFirebase(productTaskRepository)
    }

    @Provides
    fun provideUseGetProductTasksFromFirebase(
        userRepository: UserRepository,
        coroutineScope: CoroutineScope
    ): UseObserveProductTasksFirebase {
        return UseObserveProductTasksFirebase(
            userRepository = userRepository,
            coroutineScope = coroutineScope
        )
    }

    @Provides
    fun provideUseSyncProductTasks(
        productTaskRepository: ProductTaskRepository,
        coroutineScope: CoroutineScope
    ):UseSyncProductTasks{
        return UseSyncProductTasks(
            productTaskRepository = productTaskRepository,
            coroutineScope = coroutineScope
        )
    }

}