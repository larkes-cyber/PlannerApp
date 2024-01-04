package com.example.mywaycompose.data.remote.firebase_model

import com.google.gson.annotations.SerializedName

data class FirebaseVisualTask(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("text")
    val text: String? = null,
    @field:SerializedName("dependIds")
    val dependIds: String? = null,
    @field:SerializedName("mainTaskId")
    val mainTaskId:String? = null,
    @field:SerializedName("parentId")
    val parentId:String? = null
)






