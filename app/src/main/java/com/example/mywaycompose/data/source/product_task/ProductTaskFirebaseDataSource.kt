package com.example.mywaycompose.data.source.product_task

import com.example.mywaycompose.data.remote.firebase_model.FirebaseProductTask

interface ProductTaskFirebaseDataSource {

    fun pushProductTask(productTask: FirebaseProductTask)
    fun deleteProductTask(productTask: FirebaseProductTask)

}