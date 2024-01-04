package com.example.mywaycompose.data.source.product_task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseProductTask
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.example.mywaycompose.utils.Constants
import com.google.firebase.database.FirebaseDatabase

class ProductTaskFirebaseDataSourceImpl(
    private val database: FirebaseDatabase,
    private val firebaseUser: FirebaseUser
):ProductTaskFirebaseDataSource {

    private val productTasksDatabaseReference =database.getReference(Constants.PRODUCT_TASKS_KIND)

    override fun pushProductTask(productTask: FirebaseProductTask) {
        productTasksDatabaseReference.child(firebaseUser.email).child(productTask.task!!).setValue(productTask)
    }

    override fun deleteProductTask(productTask: FirebaseProductTask) {
        productTasksDatabaseReference.child(firebaseUser.email).child(productTask.task!!).removeValue()
    }
}