package com.example.calculator.presentation.main

import android.content.Context
import android.content.Intent
import android.graphics.Color.red
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calculator.R
import com.example.calculator.databinding.MainActivityBinding
import com.example.calculator.di.HistoryRepositoryProvider
import com.example.calculator.di.SettingsDaoProvider
import com.example.calculator.domain.entity.ForceVibrationTypeEnum.*
import com.example.calculator.domain.entity.ResultPanelType.*
import com.example.calculator.presentation.common.BaseActivity
import com.example.calculator.presentation.history.Result
import com.example.calculator.presentation.main.Operators.*
import com.example.calculator.presentation.settings.SettingsActivity
import java.lang.Exception


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
    private val viewBinding by viewBinding(MainActivityBinding::bind)

    private val resultLauncher = registerForActivityResult(Result()) { item ->
        viewModel.onHistoryResult(item)
    }

    private var forceVibrationValue: Long = VibrationMSTypes.WEAK.value;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

        setContentView(R.layout.main_activity)
        viewBinding.settings.setOnClickListener() { openSettings() }
        viewBinding.history.setOnClickListener() { openHistory() }

        viewBinding.mainInput.apply { showSoftInputOnFocus = false }
        viewBinding.mainEquals.setOnClickListener {}

        listOf(
            viewBinding.mainZero,
            viewBinding.mainOne,
            viewBinding.mainTwo,
            viewBinding.mainThree,
            viewBinding.mainFour,
            viewBinding.mainFive,
            viewBinding.mainSix,
            viewBinding.mainSeven,
            viewBinding.mainEight,
            viewBinding.mainNine,
        ).forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.onNumberClick(index, viewBinding.mainInput.selectionStart)
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (vibrator.hasVibrator() && forceVibrationValue != VibrationMSTypes.NO.value) { // Vibrator availability checking
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                forceVibrationValue,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        ) // New vibrate method for API Level 26 or higher
                    } else {
                        vibrator.vibrate(forceVibrationValue) // Vibrate method for below API Level 26
                    }
                }
            }
        }

        viewBinding.mainBack.setOnClickListener { viewModel.onBackButtonClick(viewBinding.mainInput.selectionStart) }
        viewBinding.mainClear.setOnClickListener { viewModel.onClearButtonClick() }


        viewBinding.mainPlus.setOnClickListener {
            viewModel.onOperationClick(PLUS, viewBinding.mainInput.selectionStart)
        }

        viewBinding.mainMinus.setOnClickListener {
            viewModel.onOperationClick(MINUS, viewBinding.mainInput.selectionStart)
        }

        viewBinding.mainDivision.setOnClickListener {
            viewModel.onOperationClick(DIVIDE, viewBinding.mainInput.selectionStart)
        }

        viewBinding.mainMultiply.setOnClickListener {
            viewModel.onOperationClick(MULTIPLY, viewBinding.mainInput.selectionStart)
        }

        viewBinding.mainDegree.setOnClickListener {
            viewModel.onOperationClick(DEGREE, viewBinding.mainInput.selectionStart)
        }

        viewBinding.mainDot.setOnClickListener { viewModel.onDotButtonClick() }
        viewBinding.mainEquals.setOnClickListener { viewModel.onEqualsButtonClick() }

        viewBinding.mainSqrt.setOnClickListener { viewModel.onSqrtButtonClick() }

        viewModel.expressionState.observe(this) { state ->
            viewBinding.mainInput.setText(state.expression)
            viewBinding.mainInput.setSelection(state.index)
        }

        viewModel.resultState.observe(this) { state ->
                viewBinding.mainResult.setText(state)
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
            with(viewBinding.mainResult) {
                gravity = when (it) {
                    LEFT -> Gravity.START.or(Gravity.CENTER_VERTICAL)
                    RIGHT -> Gravity.END.or(Gravity.CENTER_VERTICAL)
                    HIDE -> gravity
                }
                isVisible = it != HIDE
            }
        }}
        catch (exc:Exception){
            viewBinding.mainResult.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
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
        resultLauncher.launch()
    }
}



