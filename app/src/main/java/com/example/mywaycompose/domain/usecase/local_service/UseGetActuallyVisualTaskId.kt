package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseGetActuallyVisualTaskId(
    private val serviceRepository: ServiceRepository
) {

    fun execute():Int = serviceRepository.getActuallyVisualTaskId()

}