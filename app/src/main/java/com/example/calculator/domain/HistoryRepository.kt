package com.example.calculator.domain

import com.example.calculator.domain.entity.HistoryItem

interface HistoryRepository {

    suspend fun getAll(): List<HistoryItem>

    suspend fun add(historyItem: HistoryItem)
}