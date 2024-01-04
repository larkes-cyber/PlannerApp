package com.example.mywaycompose.data.source.task_class

import com.example.mywaycompose.data.remote.firebase_model.FirebaseTaskClass

interface TaskClassRemoteDataSource {

    suspend fun pushTaskClass(firebaseTaskClass: FirebaseTaskClass)
    suspend fun deleteTaskClass(firebaseTaskClass: FirebaseTaskClass)

}