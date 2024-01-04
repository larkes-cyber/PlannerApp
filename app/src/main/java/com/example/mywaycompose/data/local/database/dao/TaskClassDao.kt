package com.example.mywaycompose.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mywaycompose.data.local.database.entity.TaskClassEntity
import com.example.mywaycompose.domain.model.TaskClass

@Dao
interface TaskClassDao {

    @Insert
    suspend fun insertTaskClass(taskClassEntity:TaskClassEntity)
    @Query("SELECT * FROM TASKCLASSENTITY")
    suspend fun observeTaskClasses():List<TaskClassEntity>
    @Query("DELETE FROM TaskClassEntity")
    suspend fun nukeDatabase()
    @Delete
    suspend fun deleteTaskClass(taskClassEntity: TaskClassEntity)

    @Query("SELECT * FROM TASKCLASSENTITY WHERE :id = vsTaskId")
    suspend fun findClass(id:String):TaskClassEntity?
}