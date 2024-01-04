package com.example.mywaycompose.data.source.task_class

import com.example.mywaycompose.data.remote.firebase_model.FirebaseTaskClass
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.example.mywaycompose.utils.Constants.TASK_CLASS_DATABASE
import com.google.firebase.database.FirebaseDatabase

class TaskClassRemoteDataSourceImpl(
    private val database: FirebaseDatabase,
    private val firebaseUser: FirebaseUser
):TaskClassRemoteDataSource {
    private val taskClassDatabaseReference = database.getReference(TASK_CLASS_DATABASE)
    override suspend fun pushTaskClass(firebaseTaskClass: FirebaseTaskClass) {
        taskClassDatabaseReference.child(firebaseUser.email).child(firebaseTaskClass.id!!).setValue(firebaseTaskClass)
    }

    override suspend fun deleteTaskClass(firebaseTaskClass: FirebaseTaskClass) {
        taskClassDatabaseReference.child(firebaseUser.email).child(firebaseTaskClass.id!!).removeValue()
    }
}