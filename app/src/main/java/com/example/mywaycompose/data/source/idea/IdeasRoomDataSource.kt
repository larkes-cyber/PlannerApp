package com.example.mywaycompose.data.source.idea

import com.example.mywaycompose.data.local.database.entity.IdeaEntity

interface IdeasRoomDataSource {

    suspend fun insertIdea(ideaEntity: IdeaEntity)
    suspend fun observeIdeas():List<IdeaEntity>
    suspend fun deleteIdea(ideaEntity: IdeaEntity)
    suspend fun nukeIdeaDatabase()

}