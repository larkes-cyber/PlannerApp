package com.example.mywaycompose.data.source.visual_task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask

interface VisualTaskFirebaseDataSource {
    fun pushVisualTask(visualTask: FirebaseVisualTask)
    fun deleteVisualTask(visualTask: FirebaseVisualTask)
    fun updateVisualTaskId(id:Int)
}