package com.example.calculator.di

import android.content.Context
import com.example.calculator.data.db.HistoryRepositoryImpl
import com.example.calculator.domain.HistoryRepository

object HistoryRepositoryProvider {

    private var repository: HistoryRepository? = null

    fun get(context: Context): HistoryRepository =
        repository ?: HistoryRepositoryImpl(DatabaseProvider.get(context).historyItemDao)
            .also { repository = it }
}