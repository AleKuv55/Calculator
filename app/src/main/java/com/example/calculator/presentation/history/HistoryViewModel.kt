package com.example.calculator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.domain.HistoryRepository
import com.example.calculator.domain.entity.HistoryItem
import com.example.calculator.domain.entity.ResultPanelType
import com.example.calculator.presentation.settings.SingleLiveEvent
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {


    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState: LiveData<List<HistoryItem>> = _historyItemsState

    private val _closeWithResult = SingleLiveEvent<HistoryItem>()
    val closeWithResult: LiveData<HistoryItem> = _closeWithResult


    init {
        viewModelScope.launch {
            _historyItemsState.value = historyRepository.getAll()
        }
    }

    fun onItemsClicked(historyItem: HistoryItem) {
        _closeWithResult.value = historyItem
    }

}

