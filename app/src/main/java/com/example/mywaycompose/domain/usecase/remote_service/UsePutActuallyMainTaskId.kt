package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UsePutActuallyMainTaskId(
    private val serviceRepository: ServiceRepository,
) {

    fun execute(id:Int){
        serviceRepository.putActuallyMainTaskId(id)
        serviceRepository.pushActuallyMainTaskId(id)
    }

}