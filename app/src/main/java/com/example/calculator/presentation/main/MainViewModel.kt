package com.example.calculator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.calculateExpressions
import com.example.calculator.domain.HistoryRepository
import com.example.calculator.domain.SettingsDao
import com.example.calculator.domain.entity.ForceVibrationTypeEnum
import com.example.calculator.domain.entity.FormatResultTypeEnum
import com.example.calculator.domain.entity.HistoryItem
import com.example.calculator.domain.entity.ResultPanelType
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val settingsDao: SettingsDao,
    private val historyRepository: HistoryRepository
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    private var expression: String = ""

    private val _expressionState = MutableLiveData<ExpressionState>(ExpressionState(expression, 0))
    val expressionState: LiveData<ExpressionState> = _expressionState

    private val _resultState = MutableLiveData<String>()
    val resultState: LiveData<String> = _resultState

    private val _resultPanelState = MutableLiveData<ResultPanelType>(ResultPanelType.LEFT)
    val resultPanelState: LiveData<ResultPanelType> = _resultPanelState

    private val _formatResultState =
        MutableLiveData<FormatResultTypeEnum>(FormatResultTypeEnum.MANY)
    val formatResultState: LiveData<FormatResultTypeEnum> = _formatResultState


    private val _forceVibrationState =
        MutableLiveData<ForceVibrationTypeEnum>(ForceVibrationTypeEnum.WEAK)
    val forceVibrationState: LiveData<ForceVibrationTypeEnum> = _forceVibrationState


    init {
        viewModelScope.launch {
            _resultPanelState.value = settingsDao.getResultPanelType()
            _formatResultState.value = settingsDao.getFormatResultType()
            _forceVibrationState.value = settingsDao.getForceVibrationType()
        }
    }

    fun onNumberClick(number: Int, index: Int) {
//        expression += number.toString()
        expression = expression.substring(0, index) + number.toString() + expression.substring(
            index,
            expression.length
        )
        _expressionState.value = ExpressionState(expression, index + 1)
        _resultState.value = calculateExpressions(expression, _formatResultState.value)

    }

    fun onOperationClick(operators: Operators, index: Int) {
            expression =
                expression.substring(0, index) + operators.symbol + expression.substring(
                    index,
                    expression.length
                )
            _expressionState.value = ExpressionState(expression, index + 1)
            _resultState.value = calculateExpressions(expression, _formatResultState.value)
    }

    fun onSqrtButtonClick() {
//        expression = "√" + expression
        expression += "^0.5"
        val result = calculateExpressions(expression, _formatResultState.value)
        _expressionState.value = ExpressionState(expression, expression.length)
        _resultState.value = result
    }

    fun onDegreeButtonClick() {
        TODO("Not yet implemented")
    }

    fun onBackButtonClick(index: Int) {
        expression = expression.removeRange(index - 1, index)
//        expression = expression.substring(0, index) + expression.substring(index+1, expression.length)


        _expressionState.value = ExpressionState(expression, (index - 1).coerceAtLeast(0))
        _resultState.value = calculateExpressions(expression, _formatResultState.value)
    }

    fun onEqualsButtonClick() {
        val result = calculateExpressions(expression, _formatResultState.value)
        _resultState.value = result
        viewModelScope.launch {
            historyRepository.add(HistoryItem(expression, result))
        }
        _expressionState.value = ExpressionState(result, result.length)
        expression = result
    }

    fun onClearButtonClick() {
        expression = ""
        _expressionState.value = ExpressionState(expression, 0)
        _resultState.value = expression
    }

    fun resultUpdate() {
        val result = calculateExpressions(expression, _formatResultState.value)
        _resultState.value = result
    }

    fun onStart() {
        viewModelScope.launch {
            _resultPanelState.value = settingsDao.getResultPanelType()
            _formatResultState.value = settingsDao.getFormatResultType()
            _forceVibrationState.value = settingsDao.getForceVibrationType()
            resultUpdate()
        }

    }

    fun onHistoryResult(item: HistoryItem?) {
        if (item != null) {
            expression = item.exp
            _expressionState.value = ExpressionState(expression, expression.length)
            _resultState.value = item.result
        }
    }

    fun onDotButtonClick() {
        expression += "."
        _expressionState.value = ExpressionState(expression, expression.length)
    }


}

enum class Operators(var symbol: String) {
    MINUS("-"), PLUS("+"), MULTIPLY("*"), DIVIDE("/"), SQRT("√"), DEGREE("^")
}

class ExpressionState(val expression: String, val index: Int)

