package com.example.mywaycompose.domain.usecase.visual_task

import com.example.mywaycompose.domain.mapper.toFirebaseVisualTask
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.repository.VisualTaskRepository

class UsePushVisualTaskToFirebase(
    private val visualTaskRepository: VisualTaskRepository
) {

    fun execute(visualTask: VisualTask){
        visualTaskRepository.pushVisualTaskFirebase(visualTask.toFirebaseVisualTask())
    }

}