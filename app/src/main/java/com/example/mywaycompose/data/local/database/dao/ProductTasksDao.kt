package com.example.mywaycompose.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mywaycompose.data.local.database.entity.ProductTaskEntity

@Dao
interface ProductTasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductTask(productTaskEntity: ProductTaskEntity)

    @Query("SELECT * FROM ProductTaskEntity")
    suspend fun observeAllProductTasks():List<ProductTaskEntity>

    @Delete
    suspend fun deleteProductTask(productTaskEntity: ProductTaskEntity)
    @Query("DELETE FROM ProductTaskEntity")
    suspend fun nukeProductTasksDatabase()

}