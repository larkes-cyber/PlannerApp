package com.example.mywaycompose.domain.usecase.main_task

import com.example.mywaycompose.domain.repository.ServiceRepository

class UseSaveFirebaseImage(
    private val serviceRepository: ServiceRepository
) {
    fun execute(id:String){
        val ref = serviceRepository.getImageReference(id)

        val limit: Long = 1024 * 1024 * 10

        ref.getBytes(limit).addOnSuccessListener {
            serviceRepository.saveByteArrayAsFile(id, it)
        }

    }
}