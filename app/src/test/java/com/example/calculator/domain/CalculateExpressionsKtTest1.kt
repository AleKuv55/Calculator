package com.example.calculator.domain

import com.example.calculator.data.calculateExpressions
import com.example.calculator.domain.entity.FormatResultTypeEnum
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class CalculateExpressionsKtTest1 {
    @Test
    fun testPlus() {
        val exp = "2+1"
        val result = "4"

        Assert.assertEquals(result, calculateExpressions(exp, FormatResultTypeEnum.MANY))
    }
}