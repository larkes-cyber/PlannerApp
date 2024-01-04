package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository
import java.time.LocalDate

class UseCompareDateWithCurrent(
    private val serviceRepository: ServiceRepository
) {

    fun execute(date:LocalDate, kind:Int):Boolean{
        return serviceRepository.compareDateWithCurrent(date, kind)
    }

}