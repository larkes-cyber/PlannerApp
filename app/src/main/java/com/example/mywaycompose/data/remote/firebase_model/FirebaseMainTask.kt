package com.example.mywaycompose.data.remote.firebase_model

import com.google.gson.annotations.SerializedName

data class FirebaseMainTask(
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("icon")
    val icon: String? = null,
    @field:SerializedName("id")
    val id: String? = null
)
