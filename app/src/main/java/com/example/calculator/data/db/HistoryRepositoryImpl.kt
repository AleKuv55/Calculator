package com.example.calculator.data.db

import com.example.calculator.data.db.history.HistoryItemDao
import com.example.calculator.data.db.history.HistoryItemEntity
import com.example.calculator.domain.HistoryRepository
import com.example.calculator.domain.entity.HistoryItem

class HistoryRepositoryImpl(private val historyItemDao: HistoryItemDao) : HistoryRepository {


    override suspend fun add(historyItem: HistoryItem) {
        historyItemDao.insert(historyItem.toHistoryItemEntity())
    }

    override suspend fun getAll(): List<HistoryItem> {
        return historyItemDao.getAll().map { it.toHistoryItem() }
            .sortedByDescending { it.createdAt }
    }

    private fun HistoryItem.toHistoryItemEntity() = HistoryItemEntity(
        id = 0,
        expression = exp,
        result = result,
        createdAt = createdAt
    )

    private fun HistoryItemEntity.toHistoryItem() = HistoryItem(
        exp = expression,
        result = result,
        createdAt = createdAt
    )

}