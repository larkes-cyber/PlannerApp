package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseGetMainTaskId(
    private val serviceRepository: ServiceRepository
) {

    fun execute():Int{
        return serviceRepository.getActuallyMainTaskId() + 1
    }

}