package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.data.repository.toDateString
import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.UserRepository

class UsePutStartUsingDateToFirebase(
    private val userRepository: UserRepository,
    private val serviceRepository: ServiceRepository
) {

    fun execute(){
        userRepository.pushFirstDate(serviceRepository.getCurrentDate().toDateString())
    }

}