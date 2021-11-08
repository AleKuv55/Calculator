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

        if (!formattedExp.last().isDigit()) {
            formattedExp = formattedExp.dropLast(1)
        }

//        formattedExp = sqrtParser(formattedExp)

        val solution = DoubleEvaluator().evaluate(formattedExp)

        return if (floor(solution) == solution) {
            solution.toLong().toString()
        } else {
            val result = when (formatResultType) {
                FormatResultTypeEnum.MANY -> solution.toString()
                FormatResultTypeEnum.ONE -> String.format("%.1f", solution)
                FormatResultTypeEnum.THREE -> String.format("%.3f", solution)
                null -> solution.toString()
            }
            if(result.contains(','))
                return result.replace(',','.')
            return result
        }
    } catch (exc: Exception) {
        return "Неправильное выражение"
    }
}

private fun sqrtParser(exp:String):String
{
    if (!exp.contains("√"))
        return exp
    var i:Int = 0;
    var j: Int = 0;
    var expNew = exp
    while (i != exp.length ) {
        if (exp[i].equals("√")) {
            j = i+1
            while ( exp[j].code < "9".toInt() && exp[j].code > "0".toInt()) {
                j++;
            }
            expNew = expNew.substring(0, i) + expNew.substring(i+1, j)+ "^0.5" + expNew.substring(j, expNew.length + 4)
            i++
    }}
    return expNew
}