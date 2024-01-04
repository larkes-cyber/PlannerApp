package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseGetFirstDate(
    private val serviceRepository: ServiceRepository
) {

    fun execute():String{
        return serviceRepository.getFirstDate()
    }

}