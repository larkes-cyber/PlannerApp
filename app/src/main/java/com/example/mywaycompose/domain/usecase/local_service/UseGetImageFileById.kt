package com.example.mywaycompose.domain.usecase.local_service

import com.example.mywaycompose.domain.repository.ServiceRepository
import java.io.File

class UseGetImageFileById(private val serviceRepository: ServiceRepository) {
    fun execute(id:String):File{
        return serviceRepository.getImageFileById(id)
    }
}