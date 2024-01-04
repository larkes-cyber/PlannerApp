package com.example.mywaycompose.domain.usecase.main_task

import android.net.Uri
import android.util.Log
import com.example.mywaycompose.domain.mapper.toFirebaseMainTask
import com.example.mywaycompose.domain.mapper.toMainTaskEntity
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.repository.MainTaskRepository
import com.example.mywaycompose.domain.repository.ServiceRepository

class UseUpdateMainTask(
    private val mainTaskRepository: MainTaskRepository,
    private val serviceRepository: ServiceRepository
) {

    suspend fun execute(mainTask: MainTask, photoEdited:Boolean = false){
        if(photoEdited) {
            val uri = Uri.parse(mainTask.imageSrc)
            serviceRepository.saveImage(uri, mainTask.id)
            val fileImage = serviceRepository.getImageFileById(mainTask.id)
            val image = serviceRepository.convertFileToByteArray(fileImage)
            serviceRepository.deletePhotoById(mainTask.id)
            mainTaskRepository.pushMainTaskImageFirebase(
                image = image,
                id = mainTask.id
            )
        }
        mainTaskRepository.updateMainTask(mainTask.toMainTaskEntity())
    }

}