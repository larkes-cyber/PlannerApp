package com.example.mywaycompose.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun saveFirstDate(date:String)
    fun getFirstDate():String

    fun pushFirstDate(date:String)
    fun observeFirstDate(): Flow<List<String>>

    fun googleAuthRequest(): GoogleSignInClient
    fun firebaseAuthWithGoogle(idToken:String):com.google.android.gms.tasks.Task<AuthResult>
    fun getUserAuth(): FirebaseAuth

    fun <T> getAppData(kind:String): Flow<List<T>>
}