package com.example.mywaycompose.domain.usecase.local_service

import android.net.Uri
import com.example.mywaycompose.domain.repository.ServiceRepository

class UseSaveImage(private val serviceRepository: ServiceRepository) {
    suspend fun execute(uri:Uri, id:String){
        return serviceRepository.saveImage(uri, id)
    }
}