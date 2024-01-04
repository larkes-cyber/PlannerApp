package com.example.mywaycompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mywaycompose.data.local.database.dao.*

import com.example.mywaycompose.data.local.database.entity.*


@Database(entities = [TaskEntity::class, MainTaskEntity::class, IdeaEntity::class, VisualTaskEntity::class, ProductTaskEntity::class, TaskClassEntity::class], version = 5)
abstract class AppDatabase:RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    abstract fun mainTasksDao(): MainTasksDao
    abstract fun ideasDao(): IdeasDao
    abstract fun visualTasksDao():VisualTaskDao
    abstract fun productTasksDao():ProductTasksDao
    abstract fun taskClassDao():TaskClassDao
}