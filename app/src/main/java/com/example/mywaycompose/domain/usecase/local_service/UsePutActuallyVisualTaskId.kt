package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UsePutActuallyVisualTaskId(
    private val serviceRepository: ServiceRepository
) {

    fun execute(id:Int){
        serviceRepository.putActuallyVisualTaskId(id)
    }

}