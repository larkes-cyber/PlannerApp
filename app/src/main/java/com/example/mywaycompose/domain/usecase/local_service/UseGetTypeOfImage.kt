package com.example.mywaycompose.domain.usecase.local_service

import android.net.Uri
import com.example.mywaycompose.domain.repository.ServiceRepository
import javax.inject.Inject

class UseGetTypeOfImage @Inject constructor(
    private val serviceRepository: ServiceRepository
) {

    fun execute(uri: Uri):String{
        return serviceRepository.getTypeOfImage(uri)
    }

}