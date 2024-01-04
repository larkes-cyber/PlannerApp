package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseGetGoogleSignInSetup @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<GoogleSignInClient>> = flow {
        emit(Resource.Loading())
        try {
            val res = userRepository.googleAuthRequest()
            emit(Resource.Success(res))
        }catch (e:Exception){

        }

    }

}