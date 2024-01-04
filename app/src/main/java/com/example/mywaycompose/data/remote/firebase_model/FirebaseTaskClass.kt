package com.example.mywaycompose.data.remote.firebase_model

import com.google.gson.annotations.SerializedName

class FirebaseTaskClass(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("title")
    val title:String? = null,
    @field:SerializedName("color")
    val color:Long? = null,
    @field:SerializedName("vsTaskId")
    val vsTaskId:String? = null,
)