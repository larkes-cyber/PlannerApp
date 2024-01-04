package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseCheckCorrectTime(
    private val serviceRepository: ServiceRepository
) {

    fun execute(time:String):Boolean{
        return serviceRepository.checkTimeCorrect(time)
    }

}