package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseSaveFirstDate(
    private val serviceRepository: ServiceRepository
) {

     fun execute(date:String){
         serviceRepository.saveFirstDate(date)
    }

}