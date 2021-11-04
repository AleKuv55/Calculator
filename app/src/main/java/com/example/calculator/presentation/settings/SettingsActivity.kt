package com.example.calculator.presentation.settings

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calculator.presentation.common.BaseActivity
import com.example.calculator.R
import com.example.calculator.databinding.SettingsActivityBinding
import com.example.calculator.di.SettingsDaoProvider
import com.example.calculator.domain.entity.ForceVibrationTypeEnum
import com.example.calculator.domain.entity.FormatResultTypeEnum
import com.example.calculator.domain.entity.ResultPanelType

class SettingsActivity : BaseActivity() {
    private val viewModel by viewModels<SettingsViewModel>()
    {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsViewModel(SettingsDaoProvider.getDao(this@SettingsActivity)) as T
            }
        }
    }
    private val viewBinding by viewBinding(SettingsActivityBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        viewBinding.settingsBack.setOnClickListener {
            finish()
        }
        viewBinding.resultPanelSettingsContainer.setOnClickListener {
            viewModel.onResultPanelTypeClicked()
        }
        viewModel.resultPanelState.observe(this) { state ->
            viewBinding.resultPanelSettingsDescription.text =
                resources.getStringArray(R.array.result_panel_types)[state.ordinal]
        }
        viewModel.openResultPanelAction.observe(this) { type ->
            showDialogResultPanel(type)
        }

        viewBinding.formatResultContainer.setOnClickListener {
            viewModel.onFormatResultPanelTypeClicked()
        }
        viewModel.openFormatResultAction.observe(this) { type ->
            showDialogFormatResultPanel(type)
        }
        viewModel.formatResultState.observe(this) { state ->
            viewBinding.formatResultViewDescription.text =
                resources.getStringArray(R.array.format_result_types)[state.ordinal]
        }

        viewBinding.vibrationForceContainer.setOnClickListener {
            viewModel.onForceVibrationPanelTypeClicked()
        }
        viewModel.openForceVibrationAction.observe(this) { type ->
            showDialogForceVibrationPanel(type)
        }
        viewModel.forceVibrationState.observe(this) { state ->
            viewBinding.vibrationForceViewDescription.text =
                resources.getStringArray(R.array.vibration_force_types)[state.ordinal]
        }
    }

    private fun showDialogResultPanel(type: ResultPanelType) {
        var result: Int? = null
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.settings_result_panel_title))
            .setPositiveButton(getString(R.string.ok_positive_button)) { dialog, id ->
                result?.let { viewModel.onResultPanelTypeChanged(ResultPanelType.values()[it]) }
            }
            .setNegativeButton(getString(R.string.no_negative_button)) { dialog, id -> }
            .setSingleChoiceItems(R.array.result_panel_types, type.ordinal) { dialog, id ->
                result = id
            }
            .show()
    }

    private fun showDialogFormatResultPanel(type: FormatResultTypeEnum) {
        var result: Int? = null
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.settings_result_panel_title))
            .setPositiveButton(getString(R.string.ok_positive_button)) { dialog, id ->
                result?.let { viewModel.onFormatResultChanged(FormatResultTypeEnum.values()[it]) }
            }
            .setNegativeButton(getString(R.string.no_negative_button)) { dialog, id -> }
            .setSingleChoiceItems(R.array.format_result_types, type.ordinal) { dialog, id ->
                result = id
            }
            .show()
    }

    private fun showDialogForceVibrationPanel(type: ForceVibrationTypeEnum) {
        var result: Int? = null
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.settings_result_panel_title))
            .setPositiveButton(getString(R.string.ok_positive_button)) { dialog, id ->
                result?.let { viewModel.onForceVibrationChanged(ForceVibrationTypeEnum.values()[it]) }
            }
            .setNegativeButton(getString(R.string.no_negative_button)) { dialog, id -> }
            .setSingleChoiceItems(R.array.vibration_force_types, type.ordinal) { dialog, id ->
                result = id
            }
            .show()
    }


}