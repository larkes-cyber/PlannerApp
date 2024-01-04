package com.example.mywaycompose.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mywaycompose.data.local.database.entity.VisualTaskEntity

@Dao
interface VisualTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisualTask(visualTaskEntity: VisualTaskEntity)

    @Query("SELECT * FROM VisualTaskEntity where mainTaskId = :id")
    suspend fun getAllVisualTasksByMainTaskId(id:String):List<VisualTaskEntity>

    @Update
    suspend fun updateVisualTask(visualTaskEntity: VisualTaskEntity)

    @Query("SELECT * FROM VisualTaskEntity where id = :id")
    suspend fun getVisualTaskById(id:String):VisualTaskEntity

    @Query("SELECT * FROM VisualTaskEntity")
    suspend fun getAllVisualTasks():List<VisualTaskEntity>

    @Query("SELECT * FROM VisualTaskEntity where mainTaskId = :id and parentId = -1 ")
    suspend fun findVisualMainTask(id:Int):VisualTaskEntity

    @Delete
    suspend fun deleteVisualTask(visualTaskEntity: VisualTaskEntity)
    @Query("DELETE FROM VisualTaskEntity")
    suspend fun nukeVisualTasksDatabase()

    @Query("SELECT * FROM VisualTaskEntity where parentId=:id")
    suspend fun findVsTaskByParentId(id:String):VisualTaskEntity

}