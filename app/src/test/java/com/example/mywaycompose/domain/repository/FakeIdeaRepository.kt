package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.domain.model.Idea

class FakeIdeaRepository: IdeaRepository {

    private val ideasList = mutableListOf<Idea>()

    override suspend fun addIdea(idea: Idea) {
        ideasList.add(idea)
    }

    override suspend fun getIdeas(): List<Idea> {
        return ideasList
    }

    override suspend fun deleteIdea(idea: Idea) {
        ideasList.remove(idea)
    }
}