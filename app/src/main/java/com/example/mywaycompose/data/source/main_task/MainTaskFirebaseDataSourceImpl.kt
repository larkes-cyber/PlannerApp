package com.example.mywaycompose.data.source.main_task

import android.util.Log
import com.example.mywaycompose.data.remote.firebase_model.FirebaseMainTask
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference

class MainTaskFirebaseDataSourceImpl(
    private val database: FirebaseDatabase,
    private val firebaseUser: FirebaseUser,
    private val firebaseStorageReference: StorageReference
):MainTaskFirebaseDataSource {

    private val mainTasksDatabaseReference = database.getReference("main_tasks_database")
    private val mainTasksImageStorageReference = firebaseStorageReference.child("main_tasks_image_storage")
    private val idsDatabaseReference = database.getReference("ids")

    override fun pushMainTask(mainTask: FirebaseMainTask) {
        mainTasksDatabaseReference.child(firebaseUser.email).child(mainTask.id.toString()).setValue(mainTask)
    }

    override fun pushMainTaskImage(image: ByteArray, id: String) {
        Log.d("dfdwerfgfd", id.toString())
        mainTasksImageStorageReference.child(firebaseUser.email).child(id).putBytes(image)
    }

    override fun updateMainTaskId(id: Int) {
        idsDatabaseReference.child(firebaseUser.email).child("main_task_id").setValue(id)
    }

    override fun deleteMainTask(mainTask: FirebaseMainTask) {
        mainTasksDatabaseReference.child(firebaseUser.email).child(mainTask.id.toString()).removeValue()
    }
}