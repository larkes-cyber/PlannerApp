package com.example.mywaycompose.domain.usecase.main_task
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository
import java.io.File

class UsePushMainTaskImageFirebase(
    private val mainTaskRepository: MainTaskRepository,
    private val serviceRepository: ServiceRepository
) {

    fun execute(file:File, id:String){
        val bytes = serviceRepository.convertFileToByteArray(file)
        mainTaskRepository.pushMainTaskImageFirebase(bytes, id)
    }

}