package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseSaveAppTheme(
    private val serviceRepository: ServiceRepository
) {

    fun execute(theme:String){
        serviceRepository.saveAppTheme(theme)
    }

}