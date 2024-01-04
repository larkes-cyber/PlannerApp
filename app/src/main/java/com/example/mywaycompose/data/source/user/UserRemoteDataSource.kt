package com.example.mywaycompose.data.source.user

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    fun googleAuthRequest(): GoogleSignInClient
    fun firebaseAuthWithGoogle(idToken:String):com.google.android.gms.tasks.Task<AuthResult>
    fun getUserAuth(): FirebaseAuth
    fun putStartUsingDate(date:String)
    fun getStartUsingDate(): Flow<List<String>>
    fun <T> getAppData(kind:String): Flow<List<T>>

}