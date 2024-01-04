package com.example.mywaycompose.data.remote.firebase_model

import com.google.gson.annotations.SerializedName


data class FirebaseProductTask(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("task")
    val task: String? = null,
    @field:SerializedName("goalId")
    var goalId:String = "-1"
)
