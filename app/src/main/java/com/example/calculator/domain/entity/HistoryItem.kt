package com.example.calculator.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class HistoryItem(
    val exp: String,
    val result: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Parcelable