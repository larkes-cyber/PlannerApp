package com.example.mywaycompose.domain.usecase.remote_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UsePutActuallyVisualTaskIdToFirebase(
    private val serviceRepository: ServiceRepository
) {

    fun execute(id:Int){
        serviceRepository.putActuallyVisualTaskId(id)
    }

}