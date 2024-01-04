package com.example.mywaycompose.domain.usecase.idea

import com.example.mywaycompose.domain.mapper.toFirebaseIdea
import com.example.mywaycompose.domain.mapper.toIdeaEntity
import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.domain.repository.IdeaRepository

class UseDeleteIdea(
    private val ideaRepository: IdeaRepository
) {

    suspend fun execute(idea:Idea){
        ideaRepository.deleteIdea(idea.toIdeaEntity())
        ideaRepository.deleteIdeaFirebase(idea.toFirebaseIdea())
    }

}