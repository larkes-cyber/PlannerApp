package com.example.mywaycompose.data.source.main_task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseMainTask

interface MainTaskFirebaseDataSource {

    fun pushMainTask(mainTask: FirebaseMainTask)
    fun pushMainTaskImage(image:ByteArray, id:String)
    fun updateMainTaskId(id:Int)
    fun deleteMainTask(mainTask: FirebaseMainTask)

}