package com.example.mywaycompose.data.source.idea

import android.util.Log
import com.example.mywaycompose.data.local.database.dao.IdeasDao
import com.example.mywaycompose.data.local.database.entity.IdeaEntity

class IdeasRoomDataSourceImpl(
    private val ideasDao: IdeasDao
):IdeasRoomDataSource {
    override suspend fun insertIdea(ideaEntity: IdeaEntity) = ideasDao.addIdea(ideaEntity)

    override suspend fun observeIdeas(): List<IdeaEntity> = ideasDao.getAllIdeas()

    override suspend fun deleteIdea(ideaEntity: IdeaEntity) {
        ideasDao.deleteIdea(ideaEntity.idea)
    }

    override suspend fun nukeIdeaDatabase() {
        ideasDao.nukeIdeasDatabase()
    }
}