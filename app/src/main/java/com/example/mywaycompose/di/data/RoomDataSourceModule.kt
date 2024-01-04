package com.example.mywaycompose.di.data

import android.content.Context
import com.example.mywaycompose.data.local.database.dao.*
import com.example.mywaycompose.data.source.idea.IdeasRoomDataSource
import com.example.mywaycompose.data.source.idea.IdeasRoomDataSourceImpl

import com.example.mywaycompose.data.source.main_task.MainTasksRoomDataSource
import com.example.mywaycompose.data.source.main_task.MainTasksRoomDataSourceImpl
import com.example.mywaycompose.data.source.product_task.ProductTasksRoomDataSource
import com.example.mywaycompose.data.source.product_task.ProductTasksRoomDataSourceImpl
import com.example.mywaycompose.data.source.service.*
import com.example.mywaycompose.data.source.task.TasksRoomDataSource
import com.example.mywaycompose.data.source.task.TasksRoomDataSourceImpl
import com.example.mywaycompose.data.source.task_class.TaskClassDatabaseDataSource
import com.example.mywaycompose.data.source.task_class.TaskClassDatabaseDataSourceImpl
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSourceImpl
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSource
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSourceImpl
import com.example.mywaycompose.data.source.visual_task.VisualTaskRoomDataSource
import com.example.mywaycompose.data.source.visual_task.VisualTaskRoomDataSourceImpl
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomDataSourceModule {

    @Provides
    fun provideProductTasksRoomDataSource(
        productTasksDao: ProductTasksDao
    ):ProductTasksRoomDataSource = ProductTasksRoomDataSourceImpl(
        productTasksDao = productTasksDao
    )

    @Provides
    fun provideTasksRoomDataSource(
        tasksDao: TasksDao,
        context: Context
    ):TasksRoomDataSource = TasksRoomDataSourceImpl(
        tasksDao = tasksDao,
        context = context
    )

    @Provides
    fun provideMainTasksRoomDataSource(
        mainTasksDao: MainTasksDao
    ): MainTasksRoomDataSource = MainTasksRoomDataSourceImpl(
        mainTasksDao = mainTasksDao
    )

    @Provides
    fun provideIdeasRoomDataSource(
        ideasDao: IdeasDao
    ):IdeasRoomDataSource = IdeasRoomDataSourceImpl(
        ideasDao = ideasDao
    )

    @Provides
    fun provideVisualTasksRoomDataSource(
        visualTaskDao: VisualTaskDao
    ):VisualTaskRoomDataSource = VisualTaskRoomDataSourceImpl(
        visualTaskDao = visualTaskDao
    )

    @Provides
    fun provideTaskClassDatabaseDataSource(
        taskClassDao: TaskClassDao
    ):TaskClassDatabaseDataSource = TaskClassDatabaseDataSourceImpl(taskClassDao)


}