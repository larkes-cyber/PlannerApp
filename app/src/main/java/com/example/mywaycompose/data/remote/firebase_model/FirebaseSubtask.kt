package com.example.mywaycompose.data.remote.firebase_model

import com.google.gson.annotations.SerializedName

data class FirebaseSubtask(
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("color")
    val color: String? = null,
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("mainTaskId")
    val mainTaskId: Int? = null,
)

