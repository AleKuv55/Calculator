package com.example.calculator.di

import android.content.Context
import com.example.calculator.domain.SettingsDao
import com.example.calculator.data.SettingsDaoImpl

object SettingsDaoProvider {

    private var dao: SettingsDao? = null
    fun getDao(context: Context): SettingsDao {
        return dao ?: SettingsDaoImpl(
            context.getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )
        ).also { dao = it }
    }
}