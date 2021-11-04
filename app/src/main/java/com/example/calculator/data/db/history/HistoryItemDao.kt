package com.example.calculator.data.db.history

import androidx.room.*

@Dao
interface HistoryItemDao {

    @Insert
    suspend fun insert(historyItemEntity: HistoryItemEntity)

    @Delete
    suspend fun delete(historyItemEntity: HistoryItemEntity)

    @Delete
    suspend fun delete(historyItemEntityList: List<HistoryItemEntity>)

    @Update
    suspend fun update(historyItemEntity: HistoryItemEntity)

    @Query(value = "SELECT * FROM history_item_entity")
    suspend fun getAll(): List<HistoryItemEntity>
}