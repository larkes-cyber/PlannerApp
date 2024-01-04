package com.example.mywaycompose.data.repository

import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSource
import com.example.mywaycompose.domain.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

class DataUserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userSharedPreferenceDataSource: UserSharedPreferenceDataSource
): UserRepository {
    override fun saveFirstDate(date: String) {
        userSharedPreferenceDataSource.saveFirstDate(date)
    }

    override fun getFirstDate(): String {
        return userSharedPreferenceDataSource.getFirstDate()
    }

    override fun pushFirstDate(date: String) {
        userRemoteDataSource.putStartUsingDate(date)
    }

    override fun observeFirstDate(): Flow<List<String>> {
        return userRemoteDataSource.getStartUsingDate()
    }

    override fun googleAuthRequest(): GoogleSignInClient {
        return userRemoteDataSource.googleAuthRequest()
    }

    override fun firebaseAuthWithGoogle(idToken: String): Task<AuthResult> {
        return userRemoteDataSource.firebaseAuthWithGoogle(idToken)
    }

    override fun getUserAuth(): FirebaseAuth {
        return userRemoteDataSource.getUserAuth()
    }

    override fun <T> getAppData(kind: String): Flow<List<T>> {
        return userRemoteDataSource.getAppData(kind)
    }
}