package com.example.calculator.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calculator.R
import com.example.calculator.presentation.common.BaseActivity
import com.example.calculator.databinding.MainActivityBinding
import com.example.calculator.di.HistoryRepositoryProvider
import com.example.calculator.di.SettingsDaoProvider
import com.example.calculator.domain.entity.ForceVibrationTypeEnum
import com.example.calculator.domain.entity.ForceVibrationTypeEnum.*
import com.example.calculator.domain.entity.ResultPanelType.LEFT
import com.example.calculator.domain.entity.ResultPanelType.RIGHT
import com.example.calculator.domain.entity.ResultPanelType.HIDE
import com.example.calculator.presentation.history.Result
import com.example.calculator.presentation.main.Operators.*
import com.example.calculator.presentation.settings.SettingsActivity


class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(
                    SettingsDaoProvider.getDao(this@MainActivity),
                    HistoryRepositoryProvider.get(this@MainActivity)
                ) as T
            }
        }
    }
    private val _viewBinding by viewBinding(MainActivityBinding::bind)

    //    private val getResult: ActivityResultLauncher<Unit> =
    private val resultLauncher = registerForActivityResult(Result())
    { item ->
        viewModel.onHistoryResult(item)
    }

    private var forceVibrationValue: Long = VibrationMSTypes.WEAK.value;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        _viewBinding.settings.setOnClickListener() { openSettings() }
        _viewBinding.history.setOnClickListener() { openHistory() }

        _viewBinding.mainInput.apply { showSoftInputOnFocus = false }
        _viewBinding.mainEquals.setOnClickListener {}

        listOf(
            _viewBinding.mainZero,
            _viewBinding.mainOne,
            _viewBinding.mainTwo,
            _viewBinding.mainThree,
            _viewBinding.mainFour,
            _viewBinding.mainFive,
            _viewBinding.mainSix,
            _viewBinding.mainSeven,
            _viewBinding.mainEight,
            _viewBinding.mainNine,
        ).forEachIndexed { index, textView ->
            textView.setOnClickListener {

                viewModel.onNumberClick(index, _viewBinding.mainInput.selectionStart)
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (vibrator.hasVibrator() && forceVibrationValue != VibrationMSTypes.NO.value) { // Vibrator availability checking
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                forceVibrationValue,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        ) // New vibrate method for API Level 26 or higher
                        _viewBinding.mainDot.text = textView.text;
                    } else {
                        vibrator.vibrate(forceVibrationValue) // Vibrate method for below API Level 26
                    }
                }
            }
        }

        _viewBinding.mainBack.setOnClickListener { viewModel.onBackButtonClick(_viewBinding.mainInput.selectionStart) }
        _viewBinding.mainClear.setOnClickListener { viewModel.onClearButtonClick() }


        _viewBinding.mainPlus.setOnClickListener {
            viewModel.onOperationClick(
                PLUS,
                _viewBinding.mainInput.selectionStart
            )
        }
        _viewBinding.mainMinus.setOnClickListener {
            viewModel.onOperationClick(
                MINUS,
                _viewBinding.mainInput.selectionStart
            )
        }
        _viewBinding.mainDivision.setOnClickListener {
            viewModel.onOperationClick(
                DIVIDE,
                _viewBinding.mainInput.selectionStart
            )
        }
        _viewBinding.mainMultiply.setOnClickListener {
            viewModel.onOperationClick(
                MULTIPLY,
                _viewBinding.mainInput.selectionStart
            )
        }

        _viewBinding.mainEquals.setOnClickListener { viewModel.onEqualsButtonClick() }

        _viewBinding.mainSqrt.setOnClickListener { viewModel.onSqrtButtonClick() }
        _viewBinding.mainDegree.setOnClickListener {
            viewModel.onOperationClick(
                DEGREE,
                _viewBinding.mainInput.selectionStart
            )
        }


        viewModel.expressionState.observe(this) { state ->
            _viewBinding.mainInput.setText(state.expression)
            _viewBinding.mainInput.setSelection(state.index)
        }
        viewModel.resultState.observe(this) { state ->
            _viewBinding.mainResult.setText(state)
        }

        viewModel.forceVibrationState.observe(this) { state ->
            forceVibrationValue = when (state) {
                NO -> VibrationMSTypes.NO.value
                WEAK -> VibrationMSTypes.WEAK.value
                MODERATE -> VibrationMSTypes.MODERATE.value
                STRONG -> VibrationMSTypes.STRONG.value
            }
        }
        viewModel.resultPanelState.observe(this) {
            with(_viewBinding.mainResult) {
                gravity = when (it) {
                    LEFT -> Gravity.START.or(Gravity.CENTER_VERTICAL)
                    RIGHT -> Gravity.END.or(Gravity.CENTER_VERTICAL)
                    HIDE -> gravity
                }
                isVisible = it != HIDE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openHistory() {
//        startActivity(Intent(this, HistoryActivity::class.java))
        resultLauncher.launch()
    }

}

enum class VibrationMSTypes(val value: Long) {
    NO(0), WEAK(250), MODERATE(500), STRONG(1000)
}



