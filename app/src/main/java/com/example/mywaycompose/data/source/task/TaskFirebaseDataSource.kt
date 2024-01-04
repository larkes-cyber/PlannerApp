package com.example.mywaycompose.data.source.task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask

interface TaskFirebaseDataSource {
    fun pushTask(task: FirebaseTask)
    fun deleteTask(task: FirebaseTask)
}