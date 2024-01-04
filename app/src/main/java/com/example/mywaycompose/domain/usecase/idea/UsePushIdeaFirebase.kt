package com.example.mywaycompose.domain.usecase.idea

import com.example.mywaycompose.domain.mapper.toFirebaseIdea
import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.domain.repository.IdeaRepository

class UsePushIdeaFirebase(
    private val ideaRepository: IdeaRepository
) {

    fun execute(idea:Idea){
        ideaRepository.pushIdeaFirebase(idea.toFirebaseIdea())
    }

}