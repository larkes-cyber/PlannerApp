package com.example.mywaycompose.di.data

import android.content.Context
import androidx.room.Room
import com.example.mywaycompose.data.local.database.AppDatabase
import com.example.mywaycompose.data.local.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataDatabaseModule {

    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,"myway_database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideTasksDao(database: AppDatabase): TasksDao {
        return database.tasksDao()
    }

    @Provides
    fun provideMainTasksDao(database: AppDatabase): MainTasksDao {
        return database.mainTasksDao()
    }

    @Provides
    fun provideIdeasDao(database: AppDatabase): IdeasDao {
        return database.ideasDao()
    }



    @Provides
    fun provideVisualTaskDao(database: AppDatabase): VisualTaskDao{
        return database.visualTasksDao()
    }



    @Provides
    fun provideProductTasksDao(
        database: AppDatabase
    ):ProductTasksDao = database.productTasksDao()


    @Provides
    fun provideTaskClassDao(
        database: AppDatabase
    ):TaskClassDao = database.taskClassDao()


}