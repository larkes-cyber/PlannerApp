package com.example.mywaycompose.domain.repository

import com.example.mywaycompose.data.local.database.entity.IdeaEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea
import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IdeaRepository {

    suspend fun addIdea(idea: IdeaEntity)
    suspend fun getIdeas():List<IdeaEntity>
    suspend fun deleteIdea(idea: IdeaEntity)
    fun pushIdeaFirebase(idea: FirebaseIdea)
    fun deleteIdeaFirebase(idea:FirebaseIdea)
    fun syncIdeas():Flow<Resource<String>>

}