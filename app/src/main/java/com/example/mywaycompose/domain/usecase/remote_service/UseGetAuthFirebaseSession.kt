package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth

class UseGetAuthFirebaseSession(
    private val userRepository: UserRepository
) {

    fun execute():FirebaseAuth{
        return userRepository.getUserAuth()
    }

}