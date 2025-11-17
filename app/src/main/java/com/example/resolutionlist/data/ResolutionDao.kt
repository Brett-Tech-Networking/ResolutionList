package com.example.resolutionlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ResolutionDao {
    @Query("SELECT * FROM resolutions ORDER BY isCompleted ASC, id DESC")
    fun getAll(): Flow<List<ResolutionItem>>

    @Insert
    suspend fun insert(item: ResolutionItem)

    @Update
    suspend fun update(item: ResolutionItem)

    @Delete
    suspend fun delete(item: ResolutionItem)

    @Query("SELECT * FROM resolutions WHERE id = :id")
    suspend fun getById(id: Int): ResolutionItem?
}