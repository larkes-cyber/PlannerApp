package com.example.mywaycompose.data.remote.firebase_model

import com.google.gson.annotations.SerializedName

data class FirebaseIdea(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("idea")
    val idea: String? = null
)

