package com.example.mywaycompose.data.source.task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class TaskFirebaseDataSourceImpl(
    private val database: FirebaseDatabase,
    private val firebaseUser: FirebaseUser
    ):TaskFirebaseDataSource {

    private val tasksDatabaseReference = database.getReference("tasks_database")

    override fun pushTask(task: FirebaseTask) {
        tasksDatabaseReference.child(firebaseUser.email).child(task.date!!).child(task.time!!).setValue(task)
    }

    override fun deleteTask(task: FirebaseTask) {
        tasksDatabaseReference.child(firebaseUser.email).child(task.date!!).child(task.time!!).removeValue()
    }
}