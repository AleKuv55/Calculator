package com.example.calculator.data

import com.example.calculator.domain.entity.FormatResultTypeEnum
import com.fathzer.soft.javaluator.DoubleEvaluator
import java.lang.Exception
import kotlin.math.floor

/**
 *
 */
fun calculateExpressions(exp: String, formatResultType: FormatResultTypeEnum?): String {
    try {
        if (exp.isBlank()) return ""

        var formattedExp = exp
        while (!formattedExp.last().isDigit()) {
            formattedExp = formattedExp.dropLast(1)
        }

        val solution = DoubleEvaluator().evaluate(formattedExp)

        val result = when (formatResultType) {
            FormatResultTypeEnum.MANY -> solution
            FormatResultTypeEnum.ONE -> String.format("%.1f", solution).toDouble()
            FormatResultTypeEnum.THREE -> String.format("%.3f", solution).toDouble()
            null -> solution
        }
//        val number:Double = 0.0449999
//        val number3digits:Double = String.format("%.3f", number).toDouble()
//        val number2digits:Double = String.format("%.2f", number3digits).toDouble()
//        val solution:Double = String.format("%.1f", number2digits).toDouble()

        return if (floor(result) == result) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    } catch (exc: Exception) {
        return "$exc"
    }
}