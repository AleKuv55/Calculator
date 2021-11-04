package com.example.calculator.domain

import com.example.calculator.domain.entity.ForceVibrationTypeEnum
import com.example.calculator.domain.entity.FormatResultTypeEnum
import com.example.calculator.domain.entity.ResultPanelType

interface SettingsDao {

    suspend fun getResultPanelType(): ResultPanelType
    suspend fun setResultPanelType(resultPanelType: ResultPanelType)

    suspend fun getFormatResultType(): FormatResultTypeEnum
    suspend fun setFormatResultType(formatResultType: FormatResultTypeEnum)

    suspend fun getForceVibrationType(): ForceVibrationTypeEnum
    suspend fun setForceVibrationType(forceVibrationType: ForceVibrationTypeEnum)
}

