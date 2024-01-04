package com.example.mywaycompose.data.source.service

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface ServiceRemoteDataSource {
    fun googleAuthRequest(): GoogleSignInClient
    fun firebaseAuthWithGoogle(idToken:String):com.google.android.gms.tasks.Task<AuthResult>
    fun getUserAuth(): FirebaseAuth
    fun putActuallyMainTaskId(id:Int)
    fun deletePhotoById(id:String)
    fun getImageReference(id:String): StorageReference
    fun getCurrentIdBySomeModel(kind:String): Flow<Int>
    fun putActuallyVisualTaskId(id:Int)
}