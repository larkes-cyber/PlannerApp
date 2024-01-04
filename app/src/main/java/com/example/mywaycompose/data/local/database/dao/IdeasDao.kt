package com.example.mywaycompose.data.local.database.dao

import androidx.room.*
import com.example.mywaycompose.data.local.database.entity.IdeaEntity

@Dao
interface IdeasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIdea(ideaEntity: IdeaEntity)

    @Query("DELETE FROM IdeaEntity WHERE idea = :idea")
    suspend fun deleteIdea(idea:String)

    @Query("SELECT * FROM IdeaEntity")
    suspend fun getAllIdeas():List<IdeaEntity>

    @Query("DELETE FROM IdeaEntity")
    suspend fun nukeIdeasDatabase()

}