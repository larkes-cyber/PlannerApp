package com.example.mywaycompose.data.source.visual_task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseVisualTask
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.example.mywaycompose.utils.Constants
import com.google.firebase.database.FirebaseDatabase

class VisualTaskFirebaseDataSourceImpl(
    private val database: FirebaseDatabase,
    private val firebaseUser: FirebaseUser
):VisualTaskFirebaseDataSource {

    private val visualTasksDatabaseReference = database.getReference(Constants.VISUAL_TASK_KIND)
    private val idsDatabaseReference = database.getReference("ids")


    override fun pushVisualTask(visualTask: FirebaseVisualTask) {
        visualTasksDatabaseReference.child(firebaseUser.email).child(visualTask.id.toString()).setValue(visualTask)
    }

    override fun deleteVisualTask(visualTask: FirebaseVisualTask) {
        visualTasksDatabaseReference.child(firebaseUser.email).child(visualTask.id.toString()).removeValue()
    }

    override fun updateVisualTaskId(id: Int) {
        idsDatabaseReference.child(firebaseUser.email).child(Constants.VISUAL_TASK_ID_KIND).setValue(id)
    }

}