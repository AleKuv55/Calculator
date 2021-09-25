package com.example.calculator.fortestinng

class CalculatorClasses {
}

fun main() {
    println("Введите то, что хотите посчитать")
    var a = MainOperations()
    a.Add(5.0, 7.0)
    val line = readLine()
    println("My first line =  $line")
//    a.Do("5+10-20")
}

abstract class StackInterface {
    abstract var listOperations: List<String>
    abstract var listResults: List<Double>
    fun PrintStack() {}
    fun Push() {}
    fun Pop() {}
    fun Peek() {}
    fun Count() {}
}

class Stack : StackInterface() {
    override var listOperations: List<String>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var listResults: List<Double>
        get() = TODO("Not yet implemented")
        set(value) {}

}

class MainOperations() {
    var result: Double = 0.0
    var Stack = Stack()

    fun Do(input: String) {
    }

    fun Print(outMessage: String) {
        if (outMessage.contains("Success"))
            println(result)
        else
            println(outMessage)
    }

    fun Add(x: Double, y: Double): Int {
        val condition: Boolean = true
        return if (condition) {
            result = x + y
            Print("Success")
            0
        } else {
            Print("Error")
            -1
        }
    }

    fun Substraction(x: Double, y: Double): Int {
        return -1
    }

    fun Dividing(): Int {
        return -1
    }

    fun Multiply(): Int {
        return -1
    }

}

